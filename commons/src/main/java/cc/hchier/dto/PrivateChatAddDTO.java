package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/25 14:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateChatAddDTO {
    private Integer id;

    private String from;

    @NotBlank(message = "to null")
    private String to;

    @NotBlank(message = "content null")
    private String content;

    private Date createTime;
}
