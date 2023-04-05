package cc.hchier.task;


import cc.hchier.configuration.Properties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author by Hchier
 * @Date 2023/4/4 15:05
 */
@Component
public class PreDestroyTask {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Properties properties;

    public PreDestroyTask(RedisTemplate<String, Object> redisTemplate, Properties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @PreDestroy
    public void delete() {
        redisTemplate.delete(properties.topicTotalReadNumChart);
        redisTemplate.delete(properties.topicWeekReadNumChart);
        redisTemplate.delete(properties.topicDayReadNumChart);
    }
}