package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 9:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Wall implements Serializable {
    private Integer id;

    private String content;

    private String commenter;

    private String commentee;

    private Date createTime;

    private Boolean hidden;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}
