package cc.hchier.service;

import cc.hchier.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author by Hchier
 * @Date 2023/2/22 20:42
 */
@FeignClient(value = "service-user")
public interface UserService {
    /**
     * 增加关注量
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/incrFollowNum/{username}/{incr}")
    RestResponse<Object> incrFollowNum(@PathVariable("username") String username, @PathVariable("incr") Integer incr);

    /**
     * 增加被关注量
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/incrFollowedNum/{username}/{incr}")
    RestResponse<Object> incrFollowedNum(@PathVariable("username") String username, @PathVariable("incr") Integer incr);
}
