package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private String username;

    private String password;

    private String avatar;

    private String signature;

    private String email;

    private Boolean closed;

    private Integer favorNum;

    private Integer favoredNum;

    private Integer followNum;

    private Integer followedNum;

    private static final long serialVersionUID = 1L;

}