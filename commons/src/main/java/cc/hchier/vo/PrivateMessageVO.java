package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/25 20:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateMessageVO {
    private Integer id;

    private String from;

    private String to;

    private String content;

    private Date createTime;
}
