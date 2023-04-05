package cc.hchier.nettyTalk.message;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/2/25 14:58
 */
@Setter
@Accessors(chain = true)
public class SysRespMsg extends Message {

    public SysRespMsg() {
        super.msgType = 0;
    }
}
