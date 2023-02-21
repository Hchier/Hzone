package cc.hchier.service;

import cc.hchier.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.entity.Blog;
import cc.hchier.mapper.BlogMapper;
import cc.hchier.vo.BlogVO;
import org.springframework.stereotype.Service;

/**
 * @author by Hchier
 * @Date 2023/2/20 18:52
 */
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public RestResponse<Object> delete(int id, String publisher) {
        if (blogMapper.deleteByPrimaryKey(id, publisher) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Integer> publish(BlogPublishDTO dto) {
        if (blogMapper.insert(dto) == 1) {
            return RestResponse.ok(dto.getId());
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Object> get(int id, String currentUser) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        if ((blog.getSelfVisible() || blog.getHidden()) && !blog.getPublisher().equals(currentUser)) {
            return RestResponse.build(ResponseCode.PERMISSION_DENIED);
        }
        BlogVO blogVO = new BlogVO()
            .setId(blog.getId())
            .setPublisher(blog.getPublisher())
            .setTitle(blog.getTitle())
            .setContent(blog.getContent())
            .setFavorNum(blog.getFavorNum())
            .setCommentNum(blog.getCommentNum())
            .setRewardNum(blog.getRewardNum())
            .setSelfVisible(blog.getSelfVisible())
            .setHidden(blog.getHidden())
            .setCommentForbidden(blog.getCommentForbidden())
            .setUpdateTime(blog.getUpdateTime())
            .setTopic(blog.getTopic())
            .setUpdatePermission(blog.getPublisher().equals(currentUser));
        return RestResponse.ok(blogVO);
    }

    @Override
    public RestResponse<Object> update(BlogUpdateDTO dto) {
        if (blogMapper.updateByPrimaryKey(dto) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }
}
