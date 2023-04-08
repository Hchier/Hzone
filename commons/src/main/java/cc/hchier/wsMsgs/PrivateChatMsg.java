package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/8 13:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateChatMsg extends WsMsg {
    private Integer id;

    private String sender;

    private String receiver;

    private String content;

    private String createTime;

}
