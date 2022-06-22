package xyz.hchier.hzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@SpringBootTest
class HzoneApplicationTests {

    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Test
    void contextLoads() {
        System.out.println(Arrays.toString(redisTemplate.opsForZSet().range("china", 0, -1).toArray()));
        //System.out.println((String)redisTemplate.opsForValue().get("k3"));
    }

}
