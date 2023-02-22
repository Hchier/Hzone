package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author by Hchier
 * @Date 2023/2/22 19:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FollowCancelDTO {
    @NotNull(message = "type null")
    private Integer type;

    private String follower;

    @NotNull(message = "followee null")
    private String followee;
}
