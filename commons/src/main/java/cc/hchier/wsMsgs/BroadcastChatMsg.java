package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/16 13:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BroadcastChatMsg {
    private Integer id;

    private String sender;

    private String receiver;

    private String content;

    private String createTime;
}
