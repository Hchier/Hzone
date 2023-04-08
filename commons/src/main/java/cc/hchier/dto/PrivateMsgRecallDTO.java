package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * @author by Hchier
 * @Date 2023/4/7 19:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PrivateMsgRecallDTO {
    @NotNull(message = "id null")
    private Integer id;

    private String sender;

    @NotNull(message = "receiver null")
    private String receiver;
}
