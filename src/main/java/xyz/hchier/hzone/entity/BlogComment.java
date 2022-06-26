package xyz.hchier.hzone.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogComment implements Serializable {
    private Integer id;

    @NotBlank(message = "publisher不能为blank")
    private String publisher;

    @NotNull(message = "blogId不能为null")
    private Integer blogId;

    @NotBlank(message = "content不能为blank")
    private String content;

    private Integer commentNum;

    private Integer favorNum;

    private Boolean hidden;

    private Boolean deleted;

    @NotNull(message = "sourceComment不能为null")
    private Integer sourceComment;

    @NotNull(message = "commentOf不能为null")
    private Integer commentOf;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}