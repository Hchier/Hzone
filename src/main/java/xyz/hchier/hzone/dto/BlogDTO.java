package xyz.hchier.hzone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/23 18:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;
    /**
     * 博客点赞数量。插入时设为null
     */
    private Integer favorNum;

    /**
     * 博客评论数量。插入时设为null
     */
    private Integer commentNum;
    /**
     * 是否被管理员强制隐藏。插入时设为null
     */
    private Boolean hidden;

    private Boolean selfVisible;

    private Date updateTime;

    private List<String> tagList;

    private static final long serialVersionUID = 1L;
}
