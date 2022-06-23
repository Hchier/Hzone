package xyz.hchier.hzone.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.ConstRedis;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.dto.BlogDTO;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2022/6/23 16:13
 */
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
     * 成功发表后，带上博客id，返回RestResponse.ok()。否则返回RestResponse.fail();
     */
    @Override
    public RestResponse publish(BlogDTO blogDTO, HttpServletRequest request) {
        String username = (String) BaseUtils.getCurrentUser(request).getBody();
        if (username == null) {
            RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage());
        }
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        blog.setPublisher(username);
        blog.setUpdateTime(new Date());
        try {
            blog.setTags(objectMapper.writeValueAsString(blogDTO.getTagList()));
        } catch (JsonProcessingException e) {
            RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
        }
        int res = blogMapper.insert(blog);
        return res == 1 ?
            RestResponse.ok(blog.getId()) :
            RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
    }

    /**
     * 更新
     *
     * @param blogDTO 博客dto
     * @param request 请求
     * @return 首先判断登陆没，再从redis中根据博客id拿到作者。
     * 如果从redis中能拿到作者且作者与当前用户不为同一人，返回PERMISSION_DENIED，
     * 如果从redis中拿不到作者，就取mysql中拿，如果该id压根儿就不存在，返回BLOG_NOT_EXIST。
     * 如果该id存在就把id和与之对应的username放入redis中。
     * 如果从mysql中拿到的作者与当前用户不为同一人，返回PERMISSION_DENIED。
     * 如果还没有返回任何fail，则说明该id存在且作者与当前用户为同一人，则更新博客。
     * @throws JsonProcessingException json处理异常
     */
    @Override
    public RestResponse update(BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException {
        String currentUser = (String) BaseUtils.getCurrentUser(request).getBody();
        if (currentUser == null) {
            return RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage());
        }
        String usernameInHash = (String) redisTemplate.opsForHash().get(ConstRedis.BLOG_ID_AND_USERNAME.getContent(), String.valueOf(blogDTO.getId()));
        if (usernameInHash != null && !usernameInHash.equals(currentUser)) {
            return RestResponse.fail(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getMessage());
        }

        if (usernameInHash == null) {
            String username = blogMapper.selectUsernameById(blogDTO.getId());
            if (username == null) {
                return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
            }
            redisTemplate.opsForHash().put(ConstRedis.BLOG_ID_AND_USERNAME.getContent(), String.valueOf(blogDTO.getId()), username);
            if (!username.equals(currentUser)) {
                return RestResponse.fail(ResponseCode.PERMISSION_DENIED.getCode(), ResponseCode.PERMISSION_DENIED.getMessage());
            }
        }
        Blog blog = modelMapper.map(blogDTO, Blog.class);
        blog.setTags(objectMapper.writeValueAsString(blogDTO.getTagList()));
        blog.setUpdateTime(new Date());
        int res = blogMapper.updateByPrimaryKey(blog);
        return res == 1 ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
    }

    @Override
    public RestResponse getBlog(Integer id) {

        return null;
    }
}
