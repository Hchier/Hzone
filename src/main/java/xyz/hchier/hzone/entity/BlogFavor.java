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
public class BlogFavor implements Serializable {

    private Integer id;

    @NotBlank(message = "点赞者为空")
    private String praiser;

    @NotNull(message = "被点赞博客id为空")
    private Integer blogId;

    private Date createTime;

    private static final long serialVersionUID = 1L;


}