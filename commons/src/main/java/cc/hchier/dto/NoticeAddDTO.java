package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/23 13:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeAddDTO implements Serializable {
    private String sender;

    private Integer type;

    private String content;

    private String link;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}
