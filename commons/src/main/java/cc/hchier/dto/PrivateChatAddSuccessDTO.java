package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/4/6 15:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateChatAddSuccessDTO {
    private Integer id;

    private String from;

    private String to;

    private String content;

    private String createTime;
}
