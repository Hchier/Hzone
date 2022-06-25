package xyz.hchier.hzone.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.service.BlogFavorService;
import xyz.hchier.hzone.service.BlogService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/25 13:21
 */
@RestController
public class BlogFavorController {
    private BlogFavorService blogFavorService;
    private BlogService blogService;

    public BlogFavorController(BlogFavorService blogFavorService, BlogService blogService) {
        this.blogFavorService = blogFavorService;
        this.blogService = blogService;
    }

    @PostMapping("/api/blogFavor/favor")
    public RestResponse favor(@Valid @RequestBody BlogFavor blogFavor, HttpServletRequest request) {
        if (!blogService.blogExist(blogFavor.getBlogId())) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }
        return blogFavorService.favor(blogFavor, request);
    }

    @PostMapping("/api/blogFavor/favorCancel")
    public RestResponse favorCancel(@Valid @RequestBody BlogFavor blogFavor, HttpServletRequest request) {
        if (!blogService.blogExist(blogFavor.getBlogId())) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }
        return blogFavorService.cancelFavor(blogFavor, request);
    }
}
