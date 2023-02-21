package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author by Hchier
 * @Date 2023/2/12 12:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "username blank")
    private String username;

    @NotBlank(message = "password blank")
    private String password;

    @Email(message = "邮箱格式错误")
    @NotBlank(message = "email blank")
    private String email;

    @NotBlank(message = "验证码 blank")
    private String authCode;
}
