package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 16:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogUpdateDTO {
    @NotNull(message = "id null")
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Boolean selfVisible;

    private Boolean commentForbidden;

    private Date updateTime;
}