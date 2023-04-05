package cc.hchier.nettyTalk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;



/**
 * @author by Hchier
 * @Date 2023/2/25 15:03
 */
@Setter
@Getter
@Accessors(chain = true)
public class Message {
    protected byte msgType;

}
