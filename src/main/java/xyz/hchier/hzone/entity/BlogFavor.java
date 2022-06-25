package xyz.hchier.hzone.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogFavor implements Serializable {

    private Integer id;

    @NotBlank(message = "点赞者为空")
    private String praiser;

    @NotNull(message = "被点赞博客id为空")
    private Integer blogId;

    private Date createTime;

    private static final long serialVersionUID = 1L;


}