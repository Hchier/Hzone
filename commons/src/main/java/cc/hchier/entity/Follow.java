package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/22 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Follow implements Serializable {
    private Integer id;

    private Integer type;

    private String follower;

    private String followee;

    private Date createTime;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}
