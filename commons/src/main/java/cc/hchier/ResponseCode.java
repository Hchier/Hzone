package cc.hchier;

/**
 * @author by Hchier
 * @Date 2023/2/12 13:50
 */

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
    INVALID_PARAM(415, "无效参数"),
    AUTH_FAIL(416, "身份无效"),
    REGISTER_FAIL(417,"注册失败"),
    LOGOFF_FAIL(418,"注销失败"),

    XXX(999,"999");

    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}