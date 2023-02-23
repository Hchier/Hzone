package cc.hchier;

import cc.hchier.consts.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private int code;
    private String message;
    private T body;

    public static <K> RestResponse<K> ok() {
        return RestResponse.ok(null);
    }

    public static <K> RestResponse<K> ok(K body) {
        return build(ResponseCode.OK, body);
    }

    public static <K> RestResponse<K> fail() {
        return build(ResponseCode.FAIL);
    }

    public static <K> RestResponse<K> build(ResponseCode responseCode) {
        return build(responseCode, null);
    }

    public static <K> RestResponse<K> build(ResponseCode responseCode, K body) {
        return new RestResponse<>(responseCode.getCode(), responseCode.getMessage(), body);
    }
}

