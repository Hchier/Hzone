package xyz.hchier.hzone.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:51
 */
@Data
@AllArgsConstructor
public class RestResponse<T> {
    private int code;
    private String message;
    private T body;

    public static <K> RestResponse<K> fail(int code, String message) {
        return RestResponse.fail(code, message, null);
    }

    public static <K> RestResponse<K> fail(int code, String message, K body) {
        return new RestResponse<>(code, message, body);
    }

    public static <K> RestResponse<K> ok() {
        return RestResponse.ok(null);
    }

    public static <K> RestResponse<K> ok(K body) {
        return new RestResponse<>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), body);
    }

}
