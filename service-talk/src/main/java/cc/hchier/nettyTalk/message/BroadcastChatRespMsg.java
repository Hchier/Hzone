package cc.hchier.nettyTalk.message;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/16 13:38
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class BroadcastChatRespMsg extends Message{
    private Integer id;

    private String from;

    protected String to;

    private String content;

    private String createTime;

    public BroadcastChatRespMsg() {
        super.msgType = 6;
    }
}
