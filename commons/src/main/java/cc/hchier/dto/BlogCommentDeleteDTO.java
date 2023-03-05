package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author by Hchier
 * @Date 2023/3/5 14:02
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogCommentDeleteDTO {
    private String publisher;

    @NotNull(message = "receiver null")
    private String receiver;

    @NotNull(message = "id null")
    private Integer id;

    @NotNull(message = "blogId null")
    private Integer blogId;

    @NotNull(message = "baseComment null")
    private Integer baseComment;

    @NotNull(message = "commentOf null")
    private Integer commentOf;
}
