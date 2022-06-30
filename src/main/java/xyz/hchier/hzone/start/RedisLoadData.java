package xyz.hchier.hzone.start;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.service.BlogService;
import xyz.hchier.hzone.service.RedisService;

import java.util.List;
import java.util.ListIterator;

/**
 * @author by Hchier
 * @Date 2022/6/25 9:58
 */
@Component
public class RedisLoadData implements CommandLineRunner {
    private RedisTemplate redisTemplate;
    private RedisService redisService;

    public RedisLoadData(RedisTemplate redisTemplate, RedisService redisService) {
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
    }

    @Override
    public void run(String... args) throws Exception {
//        redisService.loadBlogIdAndUsername();
    }
}
