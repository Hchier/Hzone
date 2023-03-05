package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/22 13:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogCommentVO implements Serializable {
    private Integer id;

    private String publisher;

    private String receiver;

    private Integer blogId;

    private String content;

    private Integer commentNum;

    private Integer favorNum;


    /**
     * 当前用户：传去前端判断一些权限
     */
    private String currentUser;

    private Date createTime;

    private Boolean deletePermission;

    private static final long serialVersionUID = 1L;
}
