package xyz.hchier.hzone.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2022/8/29 22:19
 */
@Configuration
public class BindingConfig {
    private final DirectExchange directExchange;
    private final Queue emailQueue;

    public BindingConfig(DirectExchange directExchange, Queue emailQueue) {
        this.directExchange = directExchange;
        this.emailQueue = emailQueue;
    }

    @Bean
    public Binding bindingEmailQueueToDirectExchange() {
        return BindingBuilder.bind(emailQueue).to(directExchange).with("sendEmail");
    }
}
