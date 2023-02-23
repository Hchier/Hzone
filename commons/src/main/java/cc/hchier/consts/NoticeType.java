package cc.hchier.consts;

import lombok.AllArgsConstructor;

/**
 * @author hchier
 */

@AllArgsConstructor
public enum NoticeType {
    //用户发表博客后给关注了该用户的用户发通知
    BLOG_PUBLISH_NOTICE(1),

    BLOG_REPLIED_NOTICE(3);

    private final int code;

    public int getCode() {
        return code;
    }
}
