package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author by Hchier
 * @Date 2023/4/8 13:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateChatRecallMsg extends WsMsg {
    private Integer id;

    private String sender;

    private String receiver;
}
