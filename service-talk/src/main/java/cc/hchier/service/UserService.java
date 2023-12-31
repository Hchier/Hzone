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
     * 判断是否存在用户
     *
     * @param username 用户名
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/existUser/{username}")
    RestResponse<Object> existUser(@PathVariable("username") String username);

}
