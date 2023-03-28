package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by Hchier
 * @Date 2023/3/9 13:52
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UserVO {
    private String username;

    private String signature;

    private Integer blogNum;

    private Integer favorNum;

    private Integer favoredNum;

    private Integer followNum;

    private Integer followedNum;

    private Boolean followed;
}
