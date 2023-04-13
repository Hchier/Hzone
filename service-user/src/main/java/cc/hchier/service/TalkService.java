package cc.hchier.service;

import cc.hchier.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-talk")
public interface TalkService {
    /**
     * 创建channel
     *
     * @param username username
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/talk/createChannel/{username}")
    RestResponse<Object> createChannel(@PathVariable("username") String username);

    /**
     * 关闭channel
     *
     * @param username 用户名
     * @return {@link RestResponse}<{@link Boolean}>
     */
    @PostMapping("/talk/closeChannel/{username}")
    RestResponse<Boolean> closeChannel(@PathVariable("username") String username);
}
