package xyz.hchier.hzone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 13:39
 */
@RestController
public class UserController {
    private UserService userService;
    private ObjectMapper objectMapper;
    private RedisTemplate redisTemplate;

    public UserController(UserService userService, ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/register")
    public RestResponse register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public RestResponse login(@RequestBody User user, HttpServletRequest request) throws InterruptedException {
        return userService.login(user,request.getSession().getId());
    }

    @GetMapping("/userNotExist/{username}")
    public RestResponse userNotExist(@PathVariable String username){
        return userService.notExist(username);
    }

}
