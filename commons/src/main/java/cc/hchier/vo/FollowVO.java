package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author by Hchier
 * @Date 2023/2/22 20:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowVO  implements Serializable {
    private Integer id;

    private Integer type;

    private String follower;

    private String followee;

    private Boolean followed;

    private static final long serialVersionUID = 1L;
}
