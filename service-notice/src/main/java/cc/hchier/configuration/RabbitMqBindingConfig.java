package cc.hchier.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2023/2/23 14:18
 */
@Configuration
public class RabbitMqBindingConfig {
    private final DirectExchange directExchange;
    private final DirectExchange deadLetterExchange;

    private final Queue noticeQueue;
    private final Queue deadLetterQueue;

    public RabbitMqBindingConfig(DirectExchange directExchange, DirectExchange deadLetterExchange, Queue noticeQueue, Queue deadLetterQueue) {
        this.directExchange = directExchange;
        this.deadLetterExchange = deadLetterExchange;
        this.noticeQueue = noticeQueue;
        this.deadLetterQueue = deadLetterQueue;
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(noticeQueue).to(directExchange).with("sendNotice");
    }

    @Bean
    public Binding bindingDeadLetterQueueToDirectExchange() {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dead");
    }
}