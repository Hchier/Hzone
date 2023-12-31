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
    private Class<?> msgClass;

    private T body;

    public static <T> WsMsgDTO<T> build(T body) {
        return new WsMsgDTO<>(body.getClass(), body);
    }
}
