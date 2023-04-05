package cc.hchier.nettyTalk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/2/26 10:12
 */
@Setter
@Getter
@Accessors(chain = true)
@ToString
public class PongMsg extends Message {
    private boolean success;

    private String reason;

    public PongMsg() {
        super.msgType = 4;
    }
}
