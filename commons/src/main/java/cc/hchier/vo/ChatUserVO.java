package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 聊天用户VO
 *
 * @author by Hchier
 * @Date 2023/4/6 9:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ChatUserVO {
    private String sender;
    private Integer unReadNum;
}
