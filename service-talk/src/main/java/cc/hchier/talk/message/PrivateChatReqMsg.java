package cc.hchier.talk.message;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/25 15:09
 */
@Setter
@Getter
@Accessors(chain = true)
public class PrivateChatReqMsg extends Message {
    private String from;

    private String to;

    private String content;

    private Date createTime;

    public PrivateChatReqMsg() {
        super.msgType = 1;
    }

}
