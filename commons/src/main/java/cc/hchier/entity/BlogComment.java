package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/22 11:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogComment implements Serializable {
    private Integer id;

    private String publisher;

    private Integer blogId;

    private String content;

    private Integer commentNum;

    private Integer favorNum;

    private Boolean hidden;

    private Integer commentOf;

    private Date createTime;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}
