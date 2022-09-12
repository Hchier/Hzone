package xyz.hchier.hzone.config.mq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2022/8/29 22:14
 */
@Configuration
public class ExchangeConfig {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange",false,false);
    }

}
