package cc.hchier.talk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/25 15:20
 */
@Setter
@Accessors(chain = true)
@Getter
@ToString
public class PrivateChatRespMsg extends Message {
    private String from;

    private String content;

    private Date createTime;

    public PrivateChatRespMsg() {
        super.msgType = 2;
    }
}
