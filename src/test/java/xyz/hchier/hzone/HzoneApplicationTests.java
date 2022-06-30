package xyz.hchier.hzone;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.util.DigestUtils;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.mapper.BlogFavorMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.mapper.TalkMapper;
import xyz.hchier.hzone.service.RedisService;
import xyz.hchier.hzone.service.UserService;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Slf4j
@SpringBootTest
class HzoneApplicationTests {

    private RedisTemplate redisTemplate;
    private BlogMapper blogMapper;
    private UserService userService;
    private TalkMapper talkMapper;
    private BlogFavorMapper blogFavorMapper;
    private RedisService redisService;
    private ObjectMapper objectMapper;

    @Autowired
    public HzoneApplicationTests(RedisTemplate redisTemplate, BlogMapper blogMapper, UserService userService, TalkMapper talkMapper, BlogFavorMapper blogFavorMapper, RedisService redisService, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.blogMapper = blogMapper;
        this.userService = userService;
        this.talkMapper = talkMapper;
        this.blogFavorMapper = blogFavorMapper;
        this.redisService = redisService;
        this.objectMapper = objectMapper;
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
        BlogFavor blogFavor = (BlogFavor) redisTemplate.opsForValue().get("k1");
        System.out.println(blogFavor.toString());
    }

    @Test
    void blogFavorMultiInsertTest() {
        blogFavorMapper.multiInsert(new HashSet<BlogFavor>() {{
            add(new BlogFavor(null, "jack", 8, new Date()));
            add(new BlogFavor(null, "hchier", 8, new Date()));
        }});
    }

    @Test
    void HashTest() {
        Integer blogFavorNumById = redisService.getBlogFavorNumById(1);
        System.out.println(blogFavorNumById);
    }
    @Test
    void writeBlogFavorNum(){
        System.out.println(redisService.writeBlogFavorNum());
    }
    @Test
    void blogRandomTest(){
        List<Blog> blogList = blogMapper.selectRandom(10);
        System.out.println(blogList.size());
    }
    @Test
    void dateTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());

        System.out.println("dateStr:"+dateStr);
        Date parse = simpleDateFormat.parse(dateStr);
        System.out.println(simpleDateFormat.parse(dateStr));
    }
    @Test
    void getBlogById() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(new Date()));
    }
}
