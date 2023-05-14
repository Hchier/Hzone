package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.service.MailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

/**
 * @author by Hchier
 * @Date 2023/2/12 13:44
 */

@RestController
@Validated
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/notice/sendAuthCode")
    public RestResponse<Object> sendAuthCode(@Email(message = "邮箱格式错误") @RequestParam(value = "email", required = true) String email) throws MessagingException {
        mailService.sendAuthCode(email);
        return RestResponse.ok();
    }
}
