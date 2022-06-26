package xyz.hchier.hzone.service.impl;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;
import xyz.hchier.hzone.mapper.BlogCommentMapper;
import xyz.hchier.hzone.service.BlogCommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
     * 发表评论
     *
     * @param blogComment 评论
     * @return 失败返回fail(BLOG_COMMENT_PUBLISH_FAIL), 成功返回ok
     */
    @Override
    public RestResponse publish(BlogComment blogComment, HttpServletRequest request) {
        blogComment.setPublisher(BaseUtils.getCurrentUser(request));
        blogComment.setCreateTime(new Date());
        int res = blogCommentMapper.insert(blogComment);
        if (res == 0) {
            return RestResponse.fail(
                ResponseCode.BLOG_COMMENT_PUBLISH_FAIL.getCode(),
                ResponseCode.BLOG_COMMENT_PUBLISH_FAIL.getMessage());
        }
        return RestResponse.ok(blogComment.getId());
    }

    /**
     * 删除评论
     *
     * @param id      评论id
     * @param request 请求
     * @return 不是真的将记录删除，而是将deleted置为true，
     * 删除评论之前不用检验用户身份，因为只有删除自己的评论时才会满足 “id = ? and username = ?”
     */
    @Override
    public RestResponse delete(Integer id, HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        int res = blogCommentMapper.deleteByPrimaryKey(id, currentUser);
        if (res == 0) {
            return RestResponse.fail(
                ResponseCode.BLOG_COMMENT_DELETE_FAIL.getCode(),
                ResponseCode.BLOG_COMMENT_DELETE_FAIL.getMessage());
        }
        return RestResponse.ok();
    }
}
