package cc.hchier.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author hchier
 */
public interface MailService {
    /**
     * 发送邮件
     *
     * @param mimeMessage mime消息
     */
    void sendMail(MimeMessage mimeMessage);

    /**
     * 生成验证码，写入redis并发送给对应邮箱
     *
     * @param email 接收者邮箱
     * @throws MessagingException 通讯异常
     */
    void sendAuthCode(String email) throws MessagingException;
}
