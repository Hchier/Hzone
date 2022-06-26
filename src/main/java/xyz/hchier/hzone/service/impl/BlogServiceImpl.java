package xyz.hchier.hzone.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.RedisKeys;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.dto.BlogDTO;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogService;
import xyz.hchier.hzone.vo.BlogVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/23 16:13
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    private BlogMapper blogMapper;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private RedisTemplate redisTemplate;

    public BlogServiceImpl(BlogMapper blogMapper, ModelMapper modelMapper, ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        this.blogMapper = blogMapper;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 发布博客
     *
     * @param blogDTO 博客
     * @return 发表者根据session从redis中取而不是从前端传来。
     * 可能刚过拦截器那关，刚来到这儿，key就失效了，所以还要判断一下。
     * 成功发表后，将[id, username]插入redis，带上博客id，返回RestResponse.ok()。否则返回RestResponse.fail();
     */
    @Override
    public RestResponse publish(BlogDTO blogDTO, HttpServletRequest request) {
        String username = BaseUtils.getCurrentUser(request);
        if (username == null) {
            RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage());
        }
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        blog.setPublisher(username);
        blog.setUpdateTime(new Date());
        try {
            blog.setTags(objectMapper.writeValueAsString(blogDTO.getTagList()));
        } catch (JsonProcessingException e) {
            log.error("List转json失败：" + blogDTO.getTagList());
            return RestResponse.fail(ResponseCode.JSON_PROCESSING_EXCEPTION.getCode(), ResponseCode.JSON_PROCESSING_EXCEPTION.getMessage());
        }
        int res = blogMapper.insert(blog);
        if (res == 1) {
            redisTemplate.opsForHash().put(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(blog.getId()), blog.getPublisher());
            return RestResponse.ok(blog.getId());
        }
        return RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
    }


    /**
     * 判断该博客存不存在、判断博客的作者是不是当前用户。是 返回 {true, null},，否 返回{false, 各种fail}。
     *
     * @param blogId  博客id
     * @param request 请求
     * @return 首先判断登陆没，再从redis中根据博客id拿到作者。
     * 如果从redis中能拿到作者且作者与当前用户不为同一人，返回{false, PERMISSION_DENIED}，
     * 如果从redis中拿不到作者，就取mysql中拿，如果该id压根儿就不存在，返回{false, BLOG_NOT_EXIST}。
     * 如果该id存在就把id和与之对应的username放入redis中。
     * 如果从mysql中拿到的作者与当前用户不为同一人，返回{false, PERMISSION_DENIED}。
     * 如果还没有返回任何fail，则说明该id存在且作者与当前用户为同一人，返回{true, null}。
     */
    public Object[] check(Integer blogId, HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        if (currentUser == null) {
            return new Object[]{false, RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage())};
        }
        String usernameInHash = (String) redisTemplate.opsForHash().get(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(blogId));
        if (usernameInHash != null && !usernameInHash.equals(currentUser)) {
            return new Object[]{false, RestResponse.fail(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getMessage())};
        }

        if (usernameInHash == null) {
            String username = blogMapper.selectUsernameById(blogId);
            if (username == null) {
                return new Object[]{false, RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage())};
            }
            redisTemplate.opsForHash().put(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(blogId), username);
            if (!username.equals(currentUser)) {
                return new Object[]{false, RestResponse.fail(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getMessage())};
            }
        }
        return new Object[]{true, null};
    }

    /**
     * 更新
     *
     * @param blogDTO 博客dto
     * @param request 请求
     * @return 判断博客的作者是不是当前用户，不是返回fail，是则更新博客。
     * @throws JsonProcessingException json处理异常
     */
    @Override
    public RestResponse update(BlogDTO blogDTO, HttpServletRequest request) {
        Object[] check = check(blogDTO.getId(), request);
        if (!(boolean) check[0]) {
            return (RestResponse) check[1];
        }
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        try {
            blog.setTags(objectMapper.writeValueAsString(blogDTO.getTagList()));
        } catch (JsonProcessingException e) {
            log.error("json转String失败：" + blogDTO.getTagList());
            return RestResponse.fail(ResponseCode.JSON_PROCESSING_EXCEPTION.getCode(), ResponseCode.JSON_PROCESSING_EXCEPTION.getMessage());
        }
        blog.setUpdateTime(new Date());
        int res = blogMapper.updateByPrimaryKey(blog);
        return res == 1 ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
    }

    /**
     * 根据id获取博客
     *
     * @param id      博客id
     * @param request 请求
     * @return 当博客不存在时，返回RestResponse.fail(BLOG_NOT_EXIST)。
     * 当 博客仅仅自我可见或被管理员强制隐藏 且当前用户不为作者时，返回RestResponse.fail(PERMISSION_DENIED)。
     */
    @Override
    public RestResponse get(Integer id, HttpServletRequest request) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        if (blog == null) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }

        if ((blog.getSelfVisible() || blog.getHidden()) && !blog.getPublisher().equals(BaseUtils.getCurrentUser(request))) {
            return RestResponse.fail(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getMessage());
        }
        BlogVO blogVO = modelMapper.map(blog, BlogVO.class);
        List<String> list = null;
        try {
            list = objectMapper.readValue(blog.getTags(), new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("String转json失败：" + blog.getTags());
            return RestResponse.fail(ResponseCode.JSON_PROCESSING_EXCEPTION.getCode(), ResponseCode.JSON_PROCESSING_EXCEPTION.getMessage());
        }
        blogVO.setTagList(list);
        return RestResponse.ok(blog);

    }

    /**
     * 删除
     *
     * @param id      id
     * @param request 请求
     * @return 先检查博客是否存在以及博客作者与当前用户是否为同一人，不是，返回fail。是，再删除。
     * 删除成功，将redis “blogIdAndUsername”中的也删了。
     * 删除失败，只有一种情况是可接受的，
     * 即mysql中已经没有该blog了，但是redis中的“blogIdAndUsername”中还有[id, username]，将redis中的数据清了。
     * 若并非这种上述情况即mysql中有数据却无法删除，见👻了，log.error()。
     */
    @Override
    public RestResponse delete(Integer id, HttpServletRequest request) {
        Object[] checkRes = check(id, request);
        if (!(boolean) checkRes[0]) {
            return (RestResponse) checkRes[1];
        }
        int res = blogMapper.deleteByPrimaryKey(id);
        if (res == 0) {
            if (redisTemplate.opsForHash().get(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(id)) != null &&
                blogMapper.selectUsernameById(id) == null
            ) {
                redisTemplate.opsForHash().delete(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(id));
            } else {
                log.error("id为" + id + "的blog删除失败");
            }
            return RestResponse.fail(ResponseCode.BLOG_DELETE_FAIL.getCode(), ResponseCode.BLOG_DELETE_FAIL.getMessage());
        }
        redisTemplate.opsForHash().delete(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(id));
        return RestResponse.ok();
    }

    @Override
    public List<Blog> selectAllIdAndPublisher() {
        return blogMapper.selectAllIdAndUsername();
    }

    /**
     * 博客是否存在
     *
     * @param id id
     * @return 先去redis中找，有则返回返回true，无则去mysql中找，有则将相应记录插入redis并返回true，无则返回false。
     */
    @Override
    public boolean blogExist(Integer id) {
        if (redisTemplate.opsForHash().get(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(id)) != null) {
            return true;
        }
        String username = blogMapper.selectUsernameById(id);
        if (username != null) {
            redisTemplate.opsForHash().put(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), id, username);
            return true;
        }
        return false;
    }
}
