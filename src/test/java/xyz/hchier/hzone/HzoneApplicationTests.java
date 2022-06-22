package xyz.hchier.hzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.mapper.BlogMapper;

import java.util.Arrays;
import java.util.Date;

@SpringBootTest
class HzoneApplicationTests {

    private RedisTemplate redisTemplate;
    private BlogMapper blogMapper;

    @Autowired
    public HzoneApplicationTests(RedisTemplate redisTemplate, BlogMapper blogMapper) {
        this.redisTemplate = redisTemplate;
        this.blogMapper = blogMapper;
    }

    @Test
    void contextLoads() {
        int insert = blogMapper.insert(new Blog(null, "hchier", "titile1", "content1", null, null, true, null, new Date(), "[]"));
        System.out.println(insert);
    }

}
