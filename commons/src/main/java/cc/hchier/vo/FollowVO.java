package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/2/22 20:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowVO {
    private Integer id;

    private Integer type;

    private String follower;

    private String followee;
}
