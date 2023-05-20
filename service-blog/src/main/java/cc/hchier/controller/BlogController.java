package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.service.BlogService;
import cc.hchier.vo.BlogVO;
import io.seata.core.exception.TransactionException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

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

    @SneakyThrows
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

    @PostMapping("/blog/getAuthorById/{id}")
    public RestResponse<String> getAuthorById(@PathVariable Integer id) {
        return blogService.getAuthorById(id);
    }

    @PostMapping("/blog/publishedList/{username}/{pageNum}")
    public RestResponse<List<BlogVO>> getPublishedList(
        @PathVariable String username,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {
        return blogService.getPublishedList(
            username,
            req.getHeader("username"),
            pageNum * 10,
            10);
    }

    @PostMapping("/blog/favorList/{username}/{pageNum}")
    public RestResponse<List<BlogVO>> getFavoredList(
        @PathVariable String username,
        @PathVariable Integer pageNum) {
        return blogService.getFavoredList(
            username,
            pageNum * 10,
            10);
    }

    @PostMapping("/blog/listByTopic/{topic}/{pageNum}")
    public RestResponse<List<BlogVO>> getListByTopic(
        @PathVariable String topic,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {
        return blogService.getListByTopic(
            topic,
            pageNum * 10,
            10,
            req.getHeader("username"));
    }

    @PostMapping("/blog/homeList")
    public RestResponse<List<BlogVO>> getHomeList(HttpServletRequest req) {
        return blogService.getHomeList(req.getHeader("username"));
    }
}
