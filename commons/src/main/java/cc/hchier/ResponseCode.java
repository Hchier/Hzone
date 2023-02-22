package cc.hchier;

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