package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/3/29 13:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowUserVO {
    private Integer id;

    private String follower;

    private String followee;

    /**
     * 当前用户是否关注了该用户
     */
    private Boolean followed;
}
