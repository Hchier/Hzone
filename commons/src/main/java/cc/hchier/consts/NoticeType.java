package cc.hchier.consts;

import lombok.AllArgsConstructor;

/**
 * @author hchier
 */

@AllArgsConstructor
public enum NoticeType {
    //用户发表博客后给关注了该用户的用户发通知
    BLOG_PUBLISH_NOTICE(1),

    //用户关注的话题有了新内容
    TOPIC_UPDATE_NOTICE(2),
    //博客被回复
    BLOG_REPLIED_NOTICE(3),

    //博客评论被回复
    BLOG_COMMENT_REPLIED_NOTICE(4),

    //留言墙有新消息
    WALL_MESSAGE(5);

    private final int code;

    public int getCode() {
        return code;
    }
}
