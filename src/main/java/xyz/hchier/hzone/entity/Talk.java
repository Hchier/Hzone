package xyz.hchier.hzone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Talk implements Serializable {
    private Integer id;

    private String publisher;

    private String content;

    private Date createTime;

    private static final long serialVersionUID = 1L;

}