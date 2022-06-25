package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;
import xyz.hchier.hzone.mapper.BlogCommentMapper;
import xyz.hchier.hzone.service.BlogCommentService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/24 22:34
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    private BlogCommentMapper blogCommentMapper;

    public BlogCommentServiceImpl(BlogCommentMapper blogCommentMapper) {
        this.blogCommentMapper = blogCommentMapper;
    }


    /**
     * 发布评论
     *
     * @param blogComment 评论
     * @return {@link RestResponse}
     */
    @Override
    public RestResponse publish(BlogComment blogComment, HttpServletRequest request) {
        blogComment.setPublisher(BaseUtils.getCurrentUser(request));

        return null;
    }
}
