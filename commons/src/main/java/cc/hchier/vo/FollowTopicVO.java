package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/3/29 10:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowTopicVO {
    private Integer id;

    private String follower;

    private String followee;

    private Integer totalReadNum;

    /**
     * 当前用户是否关注了该话题
     */
    private Boolean followed;
}
