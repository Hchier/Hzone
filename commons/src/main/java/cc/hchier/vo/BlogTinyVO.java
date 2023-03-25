package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author by Hchier
 * @Date 2023/3/9 14:40
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BlogTinyVO  implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Integer favorNum;

    private Integer commentNum;

    private String topic;

    private static final long serialVersionUID = 1L;
}
