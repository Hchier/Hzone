package xyz.hchier.hzone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String username;

    private String password;

    private String email;

    /**
     * 是否被注销。插入时设为null
     */
    private Boolean closed;

    private static final long serialVersionUID = 1L;


}