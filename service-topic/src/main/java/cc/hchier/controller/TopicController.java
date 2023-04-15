package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.service.TopicService;
import cc.hchier.vo.TopicTinyVO;
import cc.hchier.vo.TopicVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/13 18:16
 */
@RestController
@Validated
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topic/get/{name}")
    public RestResponse<TopicVO> get(@PathVariable String name, HttpServletRequest req) {
        return topicService.get(name, req.getHeader("username"));
    }

    @PostMapping("/topic/incrReadNum/{name}/{incr}")
    public RestResponse<Object> incrReadNum(@PathVariable String name, @PathVariable Integer incr) {
        return topicService.incrReadNum(name, incr);
    }

    @PostMapping("/topic/incrDiscussionNum/{name}/{incr}")
    public RestResponse<Object> incrDiscussionNum(@PathVariable String name, @PathVariable Integer incr) {
        return topicService.incrDiscussionNum(name, incr);
    }

    @PostMapping("/topic/incrFollowedNum/{name}/{incr}")
    public RestResponse<Object> incrFollowedNum(@PathVariable String name, @PathVariable Integer incr) {
        return topicService.incrFollowedNum(name, incr);
    }

    @PostMapping("/topic/readNumChart/{type}")
    public RestResponse<List<TopicTinyVO>> getReadNumChart(@PathVariable String type) {
        return topicService.getReadNumChart(type);
    }
}
