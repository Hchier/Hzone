package xyz.hchier.hzone.service;

import javax.mail.MessagingException;

/**
 * @author by Hchier
 * @Date 2022/9/12 9:15
 */
public interface MqConsumerService {
    void getMsgFromDirectExchange(String msg) throws MessagingException;
}
