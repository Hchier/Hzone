package xyz.hchier.hzone.service;

/**
 * @author by Hchier
 * @Date 2022/8/29 22:21
 */
public interface MqProducerService {
    void sendMsgToDirectExchange(String directExchange, String routingKey, Object msg);

}
