package xyz.hchier.hzone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogComment implements Serializable {
    private Integer id;

    private String publisher;

    private Integer blogId;

    private String content;

    private Integer commentNum;

    private Integer favorNum;

    private Boolean hidden;

    private Boolean deleted;

    private Integer sourceComment;

    private Integer commentOf;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}