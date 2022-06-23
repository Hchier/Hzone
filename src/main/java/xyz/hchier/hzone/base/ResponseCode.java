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
    OK(200,"成功"),
    ERROR_UNKNOWN(450,"未知错误"),
    REGISTER_FAIL(451,"注册失败"),
    USER_NOT_EXIST(452,"用户不存在"),
    USER_ALREADY_EXIST(453,"用户已存在"),
    AUTH_FAIL(454,"用户不存在或密码错误");
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
