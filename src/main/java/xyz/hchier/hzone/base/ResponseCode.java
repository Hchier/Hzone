package xyz.hchier.hzone.base;

import lombok.AllArgsConstructor;
import lombok.Data;

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
    JSON_PROCESSING_EXCEPTION(449, "json处理异常"),
    ERROR_UNKNOWN(450, "未知错误"),
    REGISTER_FAIL(451, "注册失败"),
    USER_NOT_EXIST(452, "用户不存在"),
    USER_ALREADY_EXIST(453, "用户已存在"),
    AUTH_FAIL(454, "用户不存在或密码错误"),
    NOT_LOGGED_IN(455, "未登录"),
    PERMISSION_DENIED(456, "拒绝访问"),
    BLOG_NOT_EXIST(461, "博客不存在"),
    BLOG_DELETE_FAIL(462, "博客删除失败"),
    BLOG_FAVOR_FAIL(470, "博客点赞失败"),
    BLOG_FAVOR_CANCEL_FAIL(471, "博客取消点赞失败"),
    BLOG_FAVOR_INFO_NOT_EXIST(472,"博客点赞信息不存在"),
    BLOG_FAVOR_INFO_REPEAT(473,"博客点赞信息已存在");
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
