package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.entity.Topic;
import cc.hchier.service.TopicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public RestResponse<Object> get(@PathVariable(name = "name") String name) {
        return topicService.get(name);
    }

    @PostMapping("/topic/incrReadNum/{name}/{incr}")
    public RestResponse<Object> incrReadNum(@PathVariable(name = "name") String name, @PathVariable Integer incr) {
        return topicService.incrReadNum(name, incr);
    }

    @PostMapping("/topic/incrDiscussionNum/{name}/{incr}")
    public RestResponse<Object> incrDiscussionNum(@PathVariable(name = "name") String name, @PathVariable Integer incr) {
        return topicService.incrDiscussionNum(name, incr);
    }

    @PostMapping("/topic/incrFollowedNum/{name}/{incr}")
    public RestResponse<Object> incrFollowedNum(@PathVariable(name = "name") String name, @PathVariable Integer incr) {
        return topicService.incrFollowedNum(name, incr);
    }

    @PostMapping("/topic/totalReadNumChart")
    public RestResponse<List<Topic>> getTotalReadNumChart() {
        return topicService.getTotalReadNumChart();
    }

    @PostMapping("/topic/weekReadNumChart")
    public RestResponse<List<Topic>> geWeekReadNumChart() {
        return topicService.getWeekReadNumChart();
    }

    @PostMapping("/topic/dayReadNumChart")
    public RestResponse<List<Topic>> getDayReadNumChart() {
        return topicService.getDayReadNumChart();
    }
}
