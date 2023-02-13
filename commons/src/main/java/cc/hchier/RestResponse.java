package cc.hchier;

/**
 * @author by Hchier
 * @Date 2023/2/12 13:49
 */

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

    public static <K> RestResponse<K> ok() {
        return RestResponse.ok(null);
    }

    public static <K> RestResponse<K> ok(K body) {
        return new RestResponse<>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), body);
    }

    public static <K> RestResponse build(ResponseCode responseCode) {
        return build(responseCode, null);
    }

    public static <K> RestResponse build(ResponseCode responseCode, K body) {
        return new RestResponse<>(responseCode.getCode(), responseCode.getMessage(), body);
    }
}

