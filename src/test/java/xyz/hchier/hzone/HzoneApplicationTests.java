package xyz.hchier.hzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import xyz.hchier.hzone.base.ConstRedis;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@SpringBootTest
class HzoneApplicationTests {

    private RedisTemplate redisTemplate;
    private BlogMapper blogMapper;
    private UserService userService;

    @Autowired
    public HzoneApplicationTests(RedisTemplate redisTemplate, BlogMapper blogMapper, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.blogMapper = blogMapper;
        this.userService = userService;
    }

    @Test
    void contextLoads() {
        int insert = blogMapper.insert(new Blog(null, "hchier", "titile1", "content1", null, null, true, null, new Date(), "[]"));
        System.out.println(insert);
    }

    @Test
    void userInsert() {
        System.out.println(userService.register(new User("hchier", "123", "123", null)));
    }

    @Test
    void userNotExist() {
        System.out.println(userService.notExist("hchier1"));
    }

    @Test
    void userLogin() throws InterruptedException {
        System.out.println(userService.login(new User("hchier", "123", null, null), "hchiersid"));
    }

    @Test
    void md5(){
        System.out.println(DigestUtils.md5DigestAsHex("hchier".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void redisTest(){
        System.out.println(redisTemplate.opsForHash().get(ConstRedis.BLOG_ID_AND_USERNAME.getContent(), "7"));
    }
}
