package xyz.hchier.hzone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {
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

    private Boolean selfVisible;

    /**
     * 是否被管理员强制隐藏。插入时设为null
     */
    private Boolean hidden;

    private Date updateTime;

    private String tags;

    private static final long serialVersionUID = 1L;

}