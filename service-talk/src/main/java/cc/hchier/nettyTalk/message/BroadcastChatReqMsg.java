package cc.hchier.nettyTalk.message;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/16 11:53
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class BroadcastChatReqMsg extends Message {
    private Integer id;

    private String from;

    private String content;

    private String createTime;

    public BroadcastChatReqMsg() {
        super.msgType = 5;
    }
}
