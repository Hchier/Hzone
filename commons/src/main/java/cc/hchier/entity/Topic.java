package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic implements Serializable {

    private String name;

    private Boolean visible;

    private Integer readNum;

    private Integer discussionNum;

    private Integer followedNum;

    private static final long serialVersionUID = 1L;

}