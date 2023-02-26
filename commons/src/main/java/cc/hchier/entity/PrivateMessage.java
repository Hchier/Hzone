package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateMessage implements Serializable {
    private Integer id;

    private String from;

    private String to;

    private String content;

    private Date createTime;

    private Boolean recalled;

    private Boolean read;

    private static final long serialVersionUID = 1L;
}
