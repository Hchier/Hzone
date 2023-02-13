package cc.hchier.controller;

import cc.hchier.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.dto.UserLoginDTO;
import cc.hchier.dto.UserRegisterDTO;
import cc.hchier.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

/**
 * @author by Hchier
 * @Date 2023/2/12 19:42
 */
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public RestResponse register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.register(userRegisterDTO);
    }

    @PostMapping("/user/login")
    public RestResponse login(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletRequest req,HttpServletResponse resp) {
        if (userService.login(userLoginDTO).getCode() == RestResponse.ok().getCode()) {
            String token = UUID.randomUUID().toString();
            userService.setToken(token, userLoginDTO.getUsername());
            resp.addCookie(new Cookie("token", token));
            return RestResponse.ok();
        }
        return RestResponse.build(ResponseCode.AUTH_FAIL);
    }

    @PostMapping("/user/close")
    public RestResponse close(HttpServletRequest req){
        String username = req.getHeader("username");
        return userService.close(username);
    }
}
