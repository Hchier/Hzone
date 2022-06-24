package xyz.hchier.hzone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.dto.BlogDTO;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.service.BlogService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 17:23
 */
@RestController
public class BlogController {
    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/api/blog/publish")
    public RestResponse publish(@RequestBody BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException {
        return blogService.publish(blogDTO, request);
    }

    @PostMapping("/api/blog/update")
    public RestResponse update(@RequestBody BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException {
        return blogService.update(blogDTO, request);
    }
    @GetMapping("/api/blog/get/{id}")
    public RestResponse get(@PathVariable Integer id,HttpServletRequest request){
        return blogService.get(id,request);
    }
}
