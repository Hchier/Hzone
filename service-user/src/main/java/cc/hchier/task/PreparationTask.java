package cc.hchier.task;

import cc.hchier.configuration.Properties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 准备工作
 *
 * @author by Hchier
 * @Date 2023/2/12 21:37
 */
@Component
public class PreparationTask implements ApplicationRunner {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Properties properties;

    public PreparationTask(RedisTemplate<String, Object> redisTemplate, Properties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) {
        redisTemplate.delete(properties.zsetForTokenExpireTime);
        redisTemplate.delete(properties.hashForToken);
    }
}
