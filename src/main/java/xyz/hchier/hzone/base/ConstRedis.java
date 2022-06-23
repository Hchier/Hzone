package xyz.hchier.hzone.base;

import lombok.AllArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/6/23 19:58
 */
@AllArgsConstructor
public enum ConstRedis {
    BLOG_ID_AND_USERNAME("blogIdAndUsername");
    private final String content;

    public String getContent() {
        return content;
    }
}
