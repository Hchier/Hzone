package cc.hchier.service;

import cc.hchier.consts.NoticeType;
import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.dto.NoticeAddDTO;
import cc.hchier.entity.Blog;
import cc.hchier.mapper.BlogMapper;
import cc.hchier.vo.BlogVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 18:52
 */
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;
    private final RabbitTemplate rabbitTemplate;

    public BlogServiceImpl(BlogMapper blogMapper, RabbitTemplate rabbitTemplate) {
        this.blogMapper = blogMapper;
        this.rabbitTemplate = rabbitTemplate;
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
            rabbitTemplate.convertAndSend(
                "directExchange",
                "sendNotice",
                new NoticeAddDTO()
                    .setSender(dto.getPublisher())
                    .setType(NoticeType.BLOG_PUBLISH_NOTICE.getCode())
                    .setContent(dto.getTitle())
                    .setLink(String.valueOf(dto.getId()))
                    .setCreateTime(new Date())
            );
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
        if (blogMapper.update(dto) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<String> getAuthorById(int id) {
        return RestResponse.ok(blogMapper.getAuthorById(id));
    }

}
