package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 12:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WallAddDTO {
    private Integer id;

    @NotBlank(message = "content不能为blank")
    private String content;

    private String commenter;

    @NotBlank(message = "commentee不能为blank")
    private String commentee;

    private Date createTime;
}
