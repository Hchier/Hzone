package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/4/3 19:14
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WsMsgDTO {
    private Integer type;

    private String from;

    private String to;

    private String msg;
}
