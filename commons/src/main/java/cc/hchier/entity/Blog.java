package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 16:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Integer favorNum;

    private Integer commentNum;

    private Integer rewardNum;

    private Boolean selfVisible;

    private Boolean hidden;

    private Boolean commentForbidden;

    private Date updateTime;

    private String topic;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}
