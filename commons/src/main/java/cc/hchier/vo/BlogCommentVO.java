package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/22 13:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogCommentVO {
    private Integer id;

    private String publisher;

    private Integer blogId;

    private String content;

    private Integer commentNum;

    private Integer favorNum;

    //传去前端判断一些权限
    private String  currentUser;

    private Date createTime;

    private Boolean deletePermission;
}
