package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.service.BlogService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/20 16:00
 */
@RestController
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog/delete/{id}")
    public RestResponse<Object> delete(@PathVariable Integer id, HttpServletRequest req) {
        return blogService.delete(id, req.getHeader("username"));
    }

    @PostMapping("/blog/publish")
    public RestResponse<Integer> publish(@Valid @RequestBody BlogPublishDTO dto, HttpServletRequest req) {
        if (dto.getTopic() != null && dto.getTopic().isEmpty()) {
            dto.setTopic(null);
        }
        return blogService.publish(dto.setPublisher(req.getHeader("username")).setUpdateTime(new Date()));
    }

    @PostMapping("/blog/update")
    public RestResponse<Object> update(@RequestBody @Valid BlogUpdateDTO dto, HttpServletRequest req) {
        return blogService.update(dto.setPublisher(req.getHeader("username")).setUpdateTime(new Date()));
    }

    @PostMapping("/blog/get/{id}")
    public RestResponse<Object> get(@PathVariable Integer id, HttpServletRequest req) {
        return blogService.get(id, req.getHeader("username"));
    }
}
