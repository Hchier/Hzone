package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/4/16 11:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BroadcastMsgAddDTO {
    private Integer id;

    private String from;

    @NotBlank(message = "content blank")
    private String content;

    private Date createTime;
}
