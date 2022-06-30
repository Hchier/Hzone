package xyz.hchier.hzone.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.service.BlogService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 16:21
 */
@RestController
public class BaseController {
    private RedisTemplate redisTemplate;


    public BaseController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/getCurrentUser")
    public RestResponse getCurrentUser(HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        return currentUser != null ?
            RestResponse.ok(currentUser) :
            RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage());
    }


}
