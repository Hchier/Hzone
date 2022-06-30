package xyz.hchier.hzone.controller;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;
import xyz.hchier.hzone.service.BlogCommentService;
import xyz.hchier.hzone.service.BlogService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author by Hchier
 * @Date 2022/6/25 16:18
 */

@RestController
public class BlogCommentController {
    private BlogCommentService blogCommentService;
    private BlogService blogService;

    public BlogCommentController(BlogCommentService blogCommentService, BlogService blogService) {
        this.blogCommentService = blogCommentService;
        this.blogService = blogService;
    }

    @PostMapping("/api/blogComment/publish")
    public RestResponse publish(@Valid @RequestBody BlogComment blogComment, HttpServletRequest request) {
        System.out.println(blogComment.toString());
        System.out.println(1111);
        if (!blogService.blogExist(blogComment.getBlogId())) {
            return RestResponse.fail(ResponseCode.BLOG_NOT_EXIST.getCode(), ResponseCode.BLOG_NOT_EXIST.getMessage());
        }
        return blogCommentService.publish(blogComment, request);
    }

    @PostMapping("/api/blogComment/delete")
    public RestResponse delete(Integer id, HttpServletRequest request) throws InterruptedException {
        return blogCommentService.delete(id, request);
    }

    @GetMapping("/api/blogComment/getLimit/{blogId}/{pageNum}")
    public RestResponse getLimit(@PathVariable Integer blogId, @PathVariable Integer pageNum){
        return blogCommentService.getLimit(blogId,pageNum,5);
    }


}
