package xyz.hchier.hzone.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/24 10:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogVO implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Integer favorNum;

    private Integer commentNum;

    private Boolean selfVisible;

    private Boolean hidden;

    private Date updateTime;

    private List<String> tagList;

    private static final long serialVersionUID = 1L;
}
