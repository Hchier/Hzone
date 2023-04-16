package cc.hchier.nettyTalk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * @author by Hchier
 * @Date 2023/2/25 15:03 <br/>
 * 新增消息类型：<br/>
 * 1，继承{@link Message}，定义成员变量<br/>
 * 2，加入到{@link MsgType}中<br/>
 */
@Setter
@Getter
@Accessors(chain = true)
public class Message {
    protected byte msgType;

}
