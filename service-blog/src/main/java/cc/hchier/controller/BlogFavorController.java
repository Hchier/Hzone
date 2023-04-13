package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.service.BlogFavorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2023/2/21 22:00
 */
@RestController
public class BlogFavorController {
    private final BlogFavorService blogFavorService;

    public BlogFavorController(BlogFavorService blogFavorService) {
        this.blogFavorService = blogFavorService;
    }

    @PostMapping("/blog/favor/{id}/{author}")
    public RestResponse<Object> favor(@PathVariable Integer id, @PathVariable String author, HttpServletRequest req) throws Exception {
        return blogFavorService.favor(id, req.getHeader("username"), author);
    }

    @PostMapping("/blog/unFavor/{id}/{author}")
    public RestResponse<Object> unFavorfavor(@PathVariable Integer id, @PathVariable String author, HttpServletRequest req) throws Exception {
        return blogFavorService.unFavor(id, req.getHeader("username"), author);
    }
}
