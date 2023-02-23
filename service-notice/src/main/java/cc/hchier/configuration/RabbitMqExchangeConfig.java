package cc.hchier.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2023/2/23 14:18
 */
@Configuration
public class RabbitMqExchangeConfig {
    @Bean("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange", false, false);
    }

    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("deadLetterExchange", false, false);
    }
}