package xyz.hchier.hzone.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import xyz.hchier.hzone.service.MqProducerService;

/**
 * @author by Hchier
 * @Date 2022/8/29 22:26
 */
@Component
public class MqProducerServiceImpl implements MqProducerService {
    private final RabbitTemplate rabbitTemplate;

    public MqProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMsgToDirectExchange(String exchange, String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey,msg);
    }

}
