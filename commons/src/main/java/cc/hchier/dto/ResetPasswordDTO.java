package cc.hchier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author by Hchier
 * @Date 2023/5/12 19:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordDTO {
    @NotBlank(message = "password blank")
    private String password;

    @NotBlank(message = "repeatedPassword blank")
    private String repeatedPassword;

    @NotBlank(message = "authCode blank")
    private String authCode;

    @NotBlank(message = "email blank")
    private String email;
}
