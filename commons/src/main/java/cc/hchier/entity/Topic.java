package cc.hchier.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author hchier
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Topic implements Serializable {

    private String name;

    private Boolean visible;

    private Integer totalReadNum;

    private Integer weekReadNum;

    private Integer dayReadNum;

    private Integer discussionNum;

    private Integer followedNum;

    private static final long serialVersionUID = 1L;

}