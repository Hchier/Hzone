package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/4/15 19:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsMsgVO<T> {
    private Integer type;

    private T body;

    public static <T> WsMsgVO<T> build(int type, T body) {
        return new WsMsgVO<>(type, body);
    }
}
