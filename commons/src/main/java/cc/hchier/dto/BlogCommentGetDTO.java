package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author by Hchier
 * @Date 2023/3/5 19:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogCommentGetDTO {
    private String currentUser;

    @NotNull(message = "blogId null")
    private Integer blogId;

    @NotNull(message = "baseComment null")
    private Integer baseComment;

    @NotNull(message = "pageNum null")
    private Integer pageNum;
}
