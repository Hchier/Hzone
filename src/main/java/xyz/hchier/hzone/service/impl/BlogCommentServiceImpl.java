package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;
import xyz.hchier.hzone.mapper.BlogCommentMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogCommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/24 22:34
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    private BlogCommentMapper blogCommentMapper;
    private BlogMapper blogMapper;

    public BlogCommentServiceImpl(BlogCommentMapper blogCommentMapper, BlogMapper blogMapper) {
        this.blogCommentMapper = blogCommentMapper;
        this.blogMapper = blogMapper;
    }


    /**
     * 发表评论
     *
     * @param blogComment 评论
     * @return 失败返回fail(BLOG_COMMENT_PUBLISH_FAIL), 成功返回ok
     */
    @Transactional
    @Override
    public RestResponse publish(BlogComment blogComment, HttpServletRequest request) {
        blogComment.setPublisher(BaseUtils.getCurrentUser(request));
        blogComment.setCreateTime(new Date());
        int insertRes = blogCommentMapper.insert(blogComment);
        int updateRes = blogMapper.incrCommentNum(blogComment.getBlogId(), 1);
        if (insertRes == 0 || updateRes == 0) {
            return RestResponse.fail(
                ResponseCode.BLOG_COMMENT_PUBLISH_FAIL.getCode(),
                ResponseCode.BLOG_COMMENT_PUBLISH_FAIL.getMessage());
        }
        return RestResponse.ok(blogComment.getId());
    }

    /**
     * 删除评论
     *
     * @param blogComment      评论
     * @param request 请求
     * @return 不是真的将记录删除，而是将deleted置为true，
     * 删除评论之前不用检验用户身份，因为只有删除自己的评论时才会满足 “id = ? and username = ?”
     */
    @Transactional
    @Override
    public RestResponse delete(BlogComment blogComment, HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        int deleteRes = blogCommentMapper.deleteByPrimaryKey(blogComment.getId(), currentUser,blogComment.getBlogId());
        int updateRes = blogMapper.incrCommentNum(blogComment.getBlogId(), -1);
        if (deleteRes == 0 || updateRes == 0) {
            return RestResponse.fail(
                ResponseCode.BLOG_COMMENT_DELETE_FAIL.getCode(),
                ResponseCode.BLOG_COMMENT_DELETE_FAIL.getMessage());
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse getLimit(int blogId, int pageNum, int pageSize) {
        List<BlogComment> blogCommentList = blogCommentMapper.getLimit(blogId, pageNum * pageSize, pageSize);
        if (blogCommentList.isEmpty()) {
            return RestResponse.fail(ResponseCode.BLOG_HAS_NO_COMMENT.getCode(), ResponseCode.BLOG_HAS_NO_COMMENT.getMessage());
        }
        for (BlogComment blogComment : blogCommentList) {
            if (blogComment.getDeleted()) {
                blogComment.setContent(null);
            }
        }
        return RestResponse.ok(blogCommentList);
    }
}
