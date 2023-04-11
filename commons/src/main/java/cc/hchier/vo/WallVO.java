package cc.hchier.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 13:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WallVO implements Serializable {
    private Integer id;

    private String content;

    private String sender;

    private String createTime;

    private Boolean deletePermission;

    private Boolean hiddenPermission;

    private static final long serialVersionUID = 1L;
}
