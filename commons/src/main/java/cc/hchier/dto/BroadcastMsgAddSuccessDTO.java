package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/4/16 12:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BroadcastMsgAddSuccessDTO {
    private Integer id;

    private String from;

    private String content;

    private String createTime;
}
