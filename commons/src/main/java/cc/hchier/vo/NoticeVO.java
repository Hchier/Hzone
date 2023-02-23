package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/23 12:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO implements Serializable {
    private Integer id;

    private String sender;

    private String receiver;

    private Integer type;

    private String content;

    private String link;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}
