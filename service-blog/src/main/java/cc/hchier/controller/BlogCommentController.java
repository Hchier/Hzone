package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.BlogCommentDeleteDTO;
import cc.hchier.dto.BlogCommentGetDTO;
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
    public RestResponse<BlogCommentVO> publish(@Valid @RequestBody BlogCommentPublishDTO dto, HttpServletRequest req) throws TransactionException {
        return blogCommentService.publish(dto.setPublisher(req.getHeader("username")).setCreateTime(new Date()));
    }

    @PostMapping("/blog/comment/delete")
    public RestResponse<Object> delete(@Valid @RequestBody BlogCommentDeleteDTO dto, HttpServletRequest req) throws TransactionException {
        return blogCommentService.delete(dto.setPublisher(req.getHeader("username")));
    }

    @PostMapping("/blog/comment/get")
    public RestResponse<List<BlogCommentVO>> get(@Valid @RequestBody BlogCommentGetDTO dto, HttpServletRequest req) {

        return blogCommentService.get(dto.setCurrentUser(req.getHeader("username")));
    }

    @PostMapping("/blog/comment/hidden/{blogId}/{commentId}")
    public RestResponse<Object> hidden(@PathVariable Integer blogId,@PathVariable Integer commentId, HttpServletRequest req) {
        return blogCommentService.hidden(blogId,commentId, req.getHeader("username"));
    }
}
