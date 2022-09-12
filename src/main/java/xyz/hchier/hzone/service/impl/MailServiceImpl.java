package xyz.hchier.hzone.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2022/9/12 9:49
 */
@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMailToNotifyFavorInfo(String emailTo) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        String context = "被点赞了！！";
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setFrom("hchier@qq.com");
            helper.setTo(new String[]{emailTo});
            helper.setSubject("点赞");
            helper.setSentDate(new Date());
            helper.setText(context, false);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
