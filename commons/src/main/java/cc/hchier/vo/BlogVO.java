package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 16:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogVO implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Integer favorNum;

    private Integer commentNum;

    private Integer rewardNum;

    private Boolean favored;

    private Boolean selfVisible;

    private Boolean hidden;

    private Boolean commentForbidden;

    private String updateTime;

    private String topic;

    private Boolean updatePermission;

    private static final long serialVersionUID = 1L;

}
