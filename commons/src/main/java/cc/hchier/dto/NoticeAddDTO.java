package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/23 13:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeAddDTO {
    private String sender;

    /**
     * 若只存在一个接收者，则在发送 NoticeAddDTO时就填进去。
     *  若存在多个接收者，则在发送 NoticeAddDTO时不管该变量，在收到 NoticeAddDTO后再查接收者
     */
    private String receiver;

    private Integer type;

    private String content;

    private String link;

    private Date createTime;


}
