package xyz.hchier.hzone.base;

import lombok.AllArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/6/23 19:58
 */
@AllArgsConstructor
public enum ConstRedis {
    /**
     * 把博客id和用户名以hash的形式存入redis
     */
    BLOG_ID_AND_USERNAME("blogIdAndUsername"),
    /**
     * 用户对博客的点赞情况
     */
    BLOG_FAVOR_OF("blogFavorOf"),
    /**
     * 用户新增的博客点赞情况
     */
    BLOG_FAVOR_TO_ADD_OF("blogFavorToAddOf"),

    /**
     * 用户取消的博客点赞情况
     */
    BLOG_FAVOR_TO_CANCEL_OF("blogFavorToCancelOf"),
    /**
     * 博客id和点赞数量
     */
    BLOG_ID_AND_FAVOR_NUM("blogIdAndFavorNum");

    private final String key;

    public String getKey() {
        return key;
    }
}
