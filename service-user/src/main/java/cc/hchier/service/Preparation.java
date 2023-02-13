package cc.hchier.service;

import cc.hchier.configuration.ConfigProperties;
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
public class Preparation implements ApplicationRunner {
    private final RedisTemplate redisTemplate;
    private ConfigProperties configProperties;

    public Preparation(RedisTemplate redisTemplate, ConfigProperties configProperties) {
        this.redisTemplate = redisTemplate;
        this.configProperties = configProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.delete(configProperties.zsetForTokenExpireTime);
        redisTemplate.delete(configProperties.hashForToken);
    }
}
