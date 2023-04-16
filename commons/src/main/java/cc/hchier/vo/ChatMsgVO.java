package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/16 15:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChatMsgVO {
    private Integer id;

    private String from;

    private String to;

    private String content;

    private String createTime;

    private Boolean fromCurrentUser;

}
