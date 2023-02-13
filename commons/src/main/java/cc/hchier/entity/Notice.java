package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * @author hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice implements Serializable {
    private Integer id;

    private String receiver;

    private Boolean type;

    private String content;

    private String link;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}