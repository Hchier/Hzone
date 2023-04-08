package cc.hchier.wsMsgs;

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

    private T body;

    public static <T> WsMsgDTO<T> build(Integer type, T body) {
        return new WsMsgDTO<>(type, body);
    }
}
