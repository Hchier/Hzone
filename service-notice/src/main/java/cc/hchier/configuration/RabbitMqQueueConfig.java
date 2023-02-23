package cc.hchier.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/2/23 14:16
 */
@Configuration
public class RabbitMqQueueConfig {
    @Bean("noticeQueue")
    public Queue queueA() {
        Map<String, Object> args = new HashMap<>(8);
        args.put("x-dead-letter-exchange", "deadLetterExchange");
        args.put("x-dead-letter-routing-key", "dead");
        return new Queue("noticeQueue", false, false, false, args);
    }

    @Bean(value = "deadLetterQueue")
    public Queue deadLetterQueue() {
        return new Queue("deadLetterQueue", false, false, false);
    }
}