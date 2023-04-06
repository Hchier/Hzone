package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/4/6 18:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsMsgDTO<T> {
    private Integer type;

    private String receiver;

    private T body;

    public static <T> WsMsgDTO<T> build(Integer type, String receiver, T body) {
        return new WsMsgDTO<>(type, receiver, body);
    }
}
