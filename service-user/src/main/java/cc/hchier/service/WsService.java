package cc.hchier.service;

import cc.hchier.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-ws")
public interface WsService {
    /**
     * 用户是否在线
     *
     * @param username 用户名
     * @return {@link RestResponse}<{@link Boolean}>
     */
    @PostMapping("/ws/isOnline/{username}")
    RestResponse<Boolean> isOnline(@PathVariable("username") String username);
}
