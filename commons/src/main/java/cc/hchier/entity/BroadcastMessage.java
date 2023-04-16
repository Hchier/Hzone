package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/4/16 12:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BroadcastMessage implements Serializable {
    private Integer id;

    private String from;

    private String content;

    private Date createTime;

    private Boolean recalled;

    private static final long serialVersionUID = 1L;
}
