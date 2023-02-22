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
 * @Date 2023/2/22 17:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FollowDTO {
    @NotNull(message = "type null")
    private Integer type;

    private String follower;

    @NotBlank(message = "followee blank")
    private String followee;

    private Date createTime;
}
