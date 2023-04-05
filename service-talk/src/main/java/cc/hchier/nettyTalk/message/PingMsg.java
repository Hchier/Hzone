package cc.hchier.nettyTalk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/2/26 10:11
 */
@Setter
@Getter
@Accessors(chain = true)
public class PingMsg extends Message {
    private String username;

    public PingMsg() {
        super.msgType = 3;
    }
}
