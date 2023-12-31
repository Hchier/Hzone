package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.BlogCommentDeleteDTO;
import cc.hchier.dto.BlogCommentGetDTO;
import cc.hchier.dto.BlogCommentPublishDTO;
import cc.hchier.vo.BlogCommentVO;
import io.seata.core.exception.TransactionException;

import java.util.List;

/**
 * @author hchier
 */
public interface BlogCommentService {
    /**
     * 发表评论
     * 2件事
     * 1，blog_comment插入评论。
     * 2，blog更新评论数。
     * 如果为评论的评论，则再加2件事(如果base_comment == comment_of，则只用+1一次)：
     * 1，blog_comment base_comment comment_num + 1
     * 2，blog_comment comment_of comment_num + 1
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link BlogCommentVO}>
     * @throws TransactionException 交易异常
     */
    RestResponse<BlogCommentVO> publish(BlogCommentPublishDTO dto) throws TransactionException;


    /**
     * 删除评
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     * @throws TransactionException 交易异常
     */
    RestResponse<Object> delete(BlogCommentDeleteDTO dto) throws TransactionException;


    /**
     * 查找某博客下的评论
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link List}<{@link BlogCommentVO}>>
     */
    RestResponse<List<BlogCommentVO>> get(BlogCommentGetDTO dto);

    /**
     * 隐藏评论
     * @param blogId   博客id
     * @param commentId   评论id
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> hidden(int blogId, int commentId, String currentUser);
}
