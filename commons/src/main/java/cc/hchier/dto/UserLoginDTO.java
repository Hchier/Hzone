package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author by Hchier
 * @Date 2023/2/12 20:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "username不能为blank")
    private String username;

    @NotBlank(message = "password不能为blank")
    private String password;
}
