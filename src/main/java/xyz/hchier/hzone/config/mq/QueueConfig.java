package xyz.hchier.hzone.config.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2022/8/29 22:14
 */
@Configuration
public class QueueConfig {
    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue", false, false, false);
    }
}
