package cc.hchier.service;

import cc.hchier.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-topic")
public interface TopicService {

    /**
     * 增加讨论num
     *
     * @param name 名字
     * @param incr 增加
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/topic/incrDiscussionNum/{name}/{incr}")
    RestResponse<Object> incrDiscussionNum(@PathVariable(name = "name") String name, @PathVariable("incr") Integer incr);
}
