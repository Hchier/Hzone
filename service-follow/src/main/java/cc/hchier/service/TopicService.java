package cc.hchier.service;

import cc.hchier.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-topic")
public interface TopicService {
    /**
     * 增加被关注量
     *
     * @param name 名字
     * @param incr 增量
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/topic/incrFollowedNum/{name}/{incr}")
    RestResponse<Object> incrFollowedNum(@PathVariable(name = "name") String name, @PathVariable(name="incr") Integer incr);
}
