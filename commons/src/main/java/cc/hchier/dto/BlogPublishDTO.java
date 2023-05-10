package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 16:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogPublishDTO {
    private Integer id;

    private String publisher;

    @NotBlank(message = "title blank")
    private String title;

    @NotBlank(message = "content blank")
    private String content;

    private Date updateTime;

    @NotBlank(message = "topic blank")
    private String topic;
}
