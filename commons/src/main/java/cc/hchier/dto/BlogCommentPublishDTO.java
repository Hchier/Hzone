package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/22 13:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BlogCommentPublishDTO {
    private Integer id;

    private String publisher;

    @NotNull(message = "blogId null")
    private Integer blogId;

    @NotBlank(message = "content blank")
    private String content;

    @NotNull(message = "commentOf null")
    private Integer commentOf;

    private Date createTime;
}
