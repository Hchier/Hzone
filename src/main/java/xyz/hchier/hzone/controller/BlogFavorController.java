package xyz.hchier.hzone.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.service.BlogFavorService;
import xyz.hchier.hzone.service.BlogService;
import xyz.hchier.hzone.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author by Hchier
 * @Date 2022/6/25 13:21
 */
@RestController
public class BlogFavorController {
    private BlogFavorService blogFavorService;
    private BlogService blogService;
    private RedisService redisService;

    public BlogFavorController(BlogFavorService blogFavorService, BlogService blogService, RedisService redisService) {
        this.blogFavorService = blogFavorService;
        this.blogService = blogService;
        this.redisService = redisService;
    }

    @PostMapping("/api/blogFavor/favor")
    public RestResponse favor(@Valid @RequestBody BlogFavor blogFavor, HttpServletRequest request) throws InterruptedException {
        if (redisService.getBlogFavorNumById(blogFavor.getBlogId()) == -1) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }
        return blogFavorService.favor(blogFavor, request);
    }

    @PostMapping("/api/blogFavor/favorCancel")
    public RestResponse favorCancel(@Valid @RequestBody BlogFavor blogFavor, HttpServletRequest request) {
        if (redisService.getBlogFavorNumById(blogFavor.getBlogId()) == -1) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }
        return blogFavorService.cancelFavor(blogFavor, request);
    }
}
