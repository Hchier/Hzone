package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * @author hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Notice implements Serializable {
    private Integer id;

    private String sender;

    private String receiver;

    private Integer type;

    private String content;

    private String link;

    private Date createTime;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;

}