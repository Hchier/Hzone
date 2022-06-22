package xyz.hchier.hzone.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Hchier
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
//      默认的连接配置
        template.setConnectionFactory(redisConnectionFactory);

//        序列化配置
//        new 一个Jackson序列化对象，用于后面的设置
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
//        用于转义
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//       创建string的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //key采用string序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key采用string序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value采用Jackson序列化方式
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        //hash的value采用Jackson序列化方式
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);

        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
