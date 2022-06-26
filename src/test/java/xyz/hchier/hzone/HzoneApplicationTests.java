package xyz.hchier.hzone;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import xyz.hchier.hzone.base.ConstRedis;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
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
    void md5() {
        System.out.println(DigestUtils.md5DigestAsHex("hchier".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void redisTest() {

        List<Object> res = (List<Object>) redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    operations.opsForSet().add("name", "duguotao");
                    operations.opsForSet().add("gender", "male");
                    operations.opsForValue().set("k1", "v1");
                    return operations.exec();
                } catch (Exception e) {
                    operations.discard();
                    log.error("失败");
                    return null;
                }
            }
        });

        System.out.println(res.toString());

    }

    @Test
    void log4jTest() {
        redisTemplate.opsForValue().set("k1", new BlogFavor());
        BlogFavor blogFavor = (BlogFavor)redisTemplate.opsForValue().get("k1");
        System.out.println(blogFavor.toString());
    }

}
