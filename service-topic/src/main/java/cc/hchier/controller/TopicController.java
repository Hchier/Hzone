package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.service.TopicService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

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
    public RestResponse add(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.add(name);
    }

    @PostMapping("/topic/get")
    public RestResponse get(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.get(name);
    }

    @PostMapping("/topic/incrReadNum")
    public RestResponse incrReadNum(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.incrReadNum(name);
    }

    @PostMapping("/topic/incrDiscussionNum")
    public RestResponse incrDiscussionNum(@NotBlank(message = "topicName为blank") @RequestParam(name = "name") String name) {
        return topicService.incrDiscussionNum(name);
    }
}
