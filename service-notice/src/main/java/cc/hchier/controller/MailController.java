package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.service.MailService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/**
 * @author by Hchier
 * @Date 2023/2/12 13:44
 */
@RestController
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/notice/sendAuthCode/{receiver}")
    public RestResponse<Object> sendAuthCode(@PathVariable String receiver) throws MessagingException {
        mailService.sendAuthCode(receiver);
        return RestResponse.ok();
    }
}
