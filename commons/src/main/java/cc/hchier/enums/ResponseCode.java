package cc.hchier.enums;

import lombok.AllArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:58
 */

@AllArgsConstructor
public enum ResponseCode {
    /**
     * 成功
     */
    OK(200, "成功"),
    FAIL(414, "失败"),
    INVALID_PARAM(415, "无效参数"),
    AUTH_FAIL(416, "身份无效"),
    TOPIC_NOT_EXIST(417, "话题不存在"),
    PERMISSION_DENIED(418, "无权访问"),

    BLOG_FAVOR_REPEAT(419, "博客重复点赞"),

    BLOG_FAVOR_NOT_EXIST(420, "博客未点赞"),

    FOLLOW_REPEAT(421, "重复关注"),

    FOLLOW_NOT_EXIST(422, "未关注"),

    TARGET_USER_NOT_EXIST(423, "目标用户不存在"),
    BLOG_NOT_EXIST(424, "博客不存在"),

    NETTY_CHANNEL_CREATE_FAIL(425,"channel创建失败"),


    XXX(999, "999");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}