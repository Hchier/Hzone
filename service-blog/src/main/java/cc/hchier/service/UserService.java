package cc.hchier.service;

import cc.hchier.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-user")
public interface UserService {

    /**
     * 博客量+1
     *
     * @param username 用户名
     * @param incr     增加
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/incrBlogNum/{username}/{incr}")
    RestResponse<Object> incrBlogNum(@PathVariable("username") String username, @PathVariable("incr") Integer incr);

    /**
     * 被点赞量 + incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/incrFavoredNum/{username}/{incr}")
    RestResponse<Object> incrFavoredNum(@PathVariable("username") String username, @PathVariable("incr") Integer incr);

    /**
     * 点赞量 + incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/incrFavorNum/{username}/{incr}")
    RestResponse<Object> incrFavorNum(@PathVariable("username") String username, @PathVariable("incr") Integer incr);
}
