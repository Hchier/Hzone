package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/4/15 16:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicVO {
    private String name;

    private String picUrl;

    private Boolean visible;

    private Integer totalReadNum;

    private Integer weekReadNum;

    private Integer dayReadNum;

    private Integer discussionNum;

    private Integer followedNum;

    private Boolean followed;
}
