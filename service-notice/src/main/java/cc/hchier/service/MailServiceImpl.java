package cc.hchier.service;

import cc.hchier.Utils;
import cc.hchier.configuration.ConfigProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author by Hchier
 * @Date 2023/2/12 13:27
 */
@Component
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final RedisTemplate redisTemplate;
    private final ConfigProperties configProperties;

    public MailServiceImpl(JavaMailSender javaMailSender, RedisTemplate redisTemplate, ConfigProperties configProperties) {
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
        this.configProperties = configProperties;
    }

    @Override
    public void sendMail(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendAuthCode(String receiver) throws MessagingException {
        String authCode = Utils.authCode(6);
        redisTemplate.opsForValue().set(receiver, authCode, configProperties.authCodeLifeCycle, TimeUnit.SECONDS);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
        String context = "<b>验证码：" + authCode + "</b>";
        helper.setFrom("hchier@qq.com");
        helper.setTo(new String[]{receiver});
        helper.setSubject("验证码");
        helper.setSentDate(new Date());
        helper.setText(context, true);
        javaMailSender.send(mimeMessage);
    }
}
