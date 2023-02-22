package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.dto.BlogCommentPublishDTO;
import cc.hchier.service.BlogCommentService;
import cc.hchier.vo.BlogCommentVO;
import io.seata.core.exception.TransactionException;
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
 * @Date 2023/2/22 10:59
 */
@RestController
public class BlogCommentController {
    private final BlogCommentService blogCommentService;

    public BlogCommentController(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @PostMapping("/blog/comment/publish")
    public RestResponse<Integer> publish(@Valid @RequestBody BlogCommentPublishDTO dto, HttpServletRequest req) throws TransactionException {
        return blogCommentService.publish(dto.setPublisher(req.getHeader("username")).setCreateTime(new Date()));
    }

    @PostMapping("/blog/comment/delete/{commentId}/{blogId}")
    public RestResponse<Object> delete(@PathVariable Integer commentId, @PathVariable Integer blogId, HttpServletRequest req) throws TransactionException {
        return blogCommentService.delete(commentId, blogId, req.getHeader("username"));
    }

    @PostMapping("/blog/comment/get/{blogId}/{commentOf}/{pageNum}")
    public RestResponse<List<BlogCommentVO>> get(
        @PathVariable Integer blogId,
        @PathVariable Integer commentOf,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {

        return blogCommentService.get(blogId, commentOf, pageNum, 20, req.getHeader("username"));
    }

    @PostMapping("/blog/comment/hidden/{commentId}/{blogId}")
    public RestResponse<Object> hidden(@PathVariable Integer commentId, @PathVariable Integer blogId, HttpServletRequest req) {
        return blogCommentService.hidden(commentId, blogId, req.getHeader("username"));
    }
}
