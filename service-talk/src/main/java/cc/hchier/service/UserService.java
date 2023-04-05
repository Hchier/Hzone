package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.PrivateChatAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.IOException;

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

    /**
     * 发送私信
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/user/sendPrivateMsg")
    RestResponse<Object> sendPrivateMsg(@Valid @RequestBody PrivateChatAddDTO dto);
}
