package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.entity.Topic;
import cc.hchier.service.TopicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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

    @PostMapping("/topic/add")
    public RestResponse<Object> add(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.add(name);
    }

    @PostMapping("/topic/get")
    public RestResponse<Object> get(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.get(name);
    }

    @PostMapping("/topic/incrReadNum")
    public RestResponse<Object> incrReadNum(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.incrReadNum(name);
    }

    @PostMapping("/topic/incrDiscussionNum")
    public RestResponse<Object> incrDiscussionNum(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.incrDiscussionNum(name);
    }

    @PostMapping("/topic/incrFollowedNum")
    public RestResponse<Object> incrFollowedNum(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name){
        return topicService.incrFollowedNum(name);
    }

    @PostMapping("/topic/totalReadNumChart")
    public RestResponse<List<Topic>> getTotalReadNumChart(){
        return topicService.getTotalReadNumChart();
    }

    @PostMapping("/topic/weekReadNumChart")
    public RestResponse<List<Topic>> geWeekReadNumChart(){
        return topicService.getWeekReadNumChart();
    }

    @PostMapping("/topic/dayReadNumChart")
    public RestResponse<List<Topic>> getDayReadNumChart(){
        return topicService.getDayReadNumChart();
    }
}
