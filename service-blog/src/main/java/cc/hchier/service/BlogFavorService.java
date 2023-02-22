package cc.hchier.service;

import cc.hchier.RestResponse;
import io.seata.core.exception.TransactionException;

/**
 * @author hchier
 */
public interface BlogFavorService {
    /**
     * 博客点赞
     * 4件事：
     * 1，user表中作者的favored_num+1
     * 2，user表中当前用户的favor_num+1
     * 3，blog表中文章的favor_num+1
     * 4，blog_favor表中插入对应的点赞信息
     *
     * @param blogId      博客id
     * @param liker 点赞者
     * @param author      作者
     * @return {@link RestResponse}<{@link Object}>
     * @throws TransactionException 交易异常
     */
    RestResponse<Object> favor(int blogId, String liker, String author) throws TransactionException;

    /**
     * 博客取消点赞
     * 4件事：
     * 1，user表中作者的favored_num-1
     * 2，user表中当前用户的favor_num-1
     * 3，blog表中文章的favor_num-1
     * 4，blog_favor表中删除对应的点赞信息
     *
     * @param blogId 博客id
     * @param liker  点赞者
     * @param author 作者
     * @return {@link RestResponse}<{@link Object}>
     * @throws TransactionException 交易异常
     */
    RestResponse<Object> unFavor(int blogId, String liker, String author) throws TransactionException;

    /**
     * 是否存在点赞记录
     *
     * @param blogId      博客id
     * @param liker 点赞者
     * @return boolean
     */
    boolean existFavor(int blogId, String liker);
}
