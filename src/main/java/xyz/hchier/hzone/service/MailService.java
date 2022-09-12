package xyz.hchier.hzone.service;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author by Hchier
 * @Date 2022/9/12 9:45
 */
public interface MailService {
    void sendMailToNotifyFavorInfo(String emailTo) throws MessagingException;
}
