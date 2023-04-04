package cc.hchier.controller;

import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.configuration.Properties;
import cc.hchier.dto.*;
import cc.hchier.service.UserService;
import cc.hchier.vo.UserVO;
import cc.hchier.ws.MyEndpoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

/**
 * @author by Hchier
 * @Date 2023/2/12 19:42
 */
@RestController
public class UserController {
    private final UserService userService;
    private final Properties properties;
    private final MyEndpoint myEndpoint;

    public UserController(UserService userService, Properties properties, MyEndpoint myEndpoint) {
        this.userService = userService;
        this.properties = properties;
        this.myEndpoint = myEndpoint;
    }

    @PostMapping("/user/register")
    public RestResponse<Object> register(@Valid @RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/user/login")
    public RestResponse<Object> login(@Valid @RequestBody UserLoginDTO dto, HttpServletResponse resp) {
        if (userService.login(dto).getCode() == RestResponse.ok().getCode()) {
            String token = UUID.randomUUID().toString();
            userService.setToken(token, dto.getUsername());
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge(properties.tokenLifeCycle * 60);
            resp.addCookie(cookie);
            return RestResponse.ok();
        }
        return RestResponse.build(ResponseCode.AUTH_FAIL);
    }

    @PostMapping("/user/close")
    public RestResponse<Object> close(HttpServletRequest req) {
        String username = req.getHeader("username");
        return userService.close(username);
    }

    @PostMapping("/user/incrBlogNum/{username}/{incr}")
    public RestResponse<Object> incrBlogNum(@PathVariable String username, @PathVariable Integer incr) {
        return userService.incrBlogNum(username, incr);
    }

    @PostMapping("/user/incrFavorNum/{username}/{incr}")
    public RestResponse<Object> incrFavorNum(@PathVariable String username, @PathVariable Integer incr) {
        return userService.incrFavorNum(username, incr);
    }

    @PostMapping("/user/incrFavoredNum/{username}/{incr}")
    public RestResponse<Object> incrFavoredNum(@PathVariable String username, @PathVariable Integer incr) {
        return userService.incrFavoredNum(username, incr);
    }

    @PostMapping("/user/incrFollowNum/{username}/{incr}")
    public RestResponse<Object> incrFollowNum(@PathVariable String username, @PathVariable Integer incr) {
        return userService.incrFollowNum(username, incr);
    }

    @PostMapping("/user/incrFollowedNum/{username}/{incr}")
    public RestResponse<Object> incrFollowedNum(@PathVariable String username, @PathVariable Integer incr) {
        return userService.incrFollowedNum(username, incr);
    }

    @PostMapping("/user/updatePwd")
    public RestResponse<Object> updatePwd(@RequestBody UserPwdUpdateDTO dto, HttpServletRequest req) {
        String username = req.getHeader("username");
        dto.setUsername(username);
        return userService.updatePwd(dto);
    }

    @PostMapping("/user/getEmailOfCurrentUser")
    public RestResponse<Object> getEmailOfCurrentUser(HttpServletRequest req) {
        return userService.getEmailOfCurrentUser(req.getHeader("username"));
    }

    @PostMapping("/user/updateEmail")
    public RestResponse<Object> updateEmail(@RequestBody UserEmailUpdateDTO dto, HttpServletRequest req) {
        String username = req.getHeader("username");
        dto.setUsername(username);
        return userService.updateEmail(dto);
    }

    @PostMapping("/user/existUser/{username}")
    public RestResponse<Object> existUser(@PathVariable String username) {
        return userService.existUser(username);
    }

    @PostMapping("/user/vo")
    public RestResponse<UserVO> getVO(HttpServletRequest req) {
        String username = req.getParameter("username");
        String currentUser = req.getHeader("username");
        if (username == null || username.isEmpty()) {
            username = currentUser;
        }
        return userService.getUserVO(username, currentUser);
    }

    @PostMapping("/user/sendPrivateMsg")
    public RestResponse<Object> sendPrivateMsg(@Valid @RequestBody WsMsgDTO dto) throws IOException {
        myEndpoint.sendMessage(dto);
        return RestResponse.ok();
    }

    @PostMapping("/user/isOnline/{username}")
    public RestResponse<Boolean> isOnline(@PathVariable String username) {
        return RestResponse.ok(myEndpoint.existUser(username));
    }
}
