package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/21 21:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogFavor implements Serializable {
    private Integer id;

    private String liker;

    private Integer blogId;

    private Date createTime;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}
