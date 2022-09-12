package xyz.hchier.hzone.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.service.MailService;
import xyz.hchier.hzone.service.MqConsumerService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2022/9/12 9:16
 */
@Service
public class MqConsumerServiceImpl implements MqConsumerService {
    private final RabbitTemplate rabbitTemplate;
    private MailService mailService;
    private JavaMailSender javaMailSender;

    public MqConsumerServiceImpl(RabbitTemplate rabbitTemplate, MailService mailService, JavaMailSender javaMailSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.mailService = mailService;
        this.javaMailSender = javaMailSender;
    }


    @Override
    @RabbitListener(queues = {"emailQueue"})
    @RabbitHandler
    public void getMsgFromDirectExchange(String msg) throws MessagingException {
        mailService.sendMailToNotifyFavorInfo(msg);
    }
}
