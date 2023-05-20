package cc.hchier.controller;

import cc.hchier.enums.ResponseCode;
import cc.hchier.response.ResourceUploadResp;
import cc.hchier.response.RestResponse;
import cc.hchier.configuration.Properties;
import cc.hchier.dto.*;
import cc.hchier.service.TalkService;
import cc.hchier.service.UserService;
import cc.hchier.sets.SuffixSet;
import cc.hchier.vo.UserVO;
import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author by Hchier
 * @Date 2023/2/12 19:42
 */
@RestController
@Slf4j
public class UserController {
    private final UserService userService;
    private final Properties properties;
    private final TalkService talkService;
    private final Snowflake snowflake;

    public UserController(UserService userService, Properties properties, TalkService talkService, Snowflake snowflake) {
        this.userService = userService;
        this.properties = properties;
        this.talkService = talkService;
        this.snowflake = snowflake;
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
            if (talkService.createChannel(dto.getUsername()).getCode() == ResponseCode.OK.getCode()) {
                return RestResponse.ok();
            } else {
                return RestResponse.build(ResponseCode.NETTY_CHANNEL_CREATE_FAIL);
            }

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

    @PostMapping("/user/loginByToken")
    public RestResponse<Object> loginByToken(HttpServletRequest req) {
        if (talkService.createChannel(req.getHeader("username")).getCode() == ResponseCode.OK.getCode()) {
            return RestResponse.ok();
        } else {
            return RestResponse.build(ResponseCode.NETTY_CHANNEL_CREATE_FAIL);
        }
    }

    @PostMapping("/user/uploadResource")
    public Object uploadResource(@RequestPart("resource") MultipartFile file, HttpServletRequest req) throws IOException {
        if (file == null) {
            log.error("file null");
            return ResourceUploadResp.fail("file null");
        }
        String suffixName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1].toLowerCase();
        StringBuilder relativePath = new StringBuilder();
        if (SuffixSet.isPic(suffixName)) {
            relativePath
                .append(properties.picPathPrefix);
        }
        if (SuffixSet.isVideo(suffixName)) {
            relativePath
                .append(properties.videoPathPrefix);
        }
        if (relativePath.length() == 0) {
            log.error("未知的后缀：" + suffixName);
            return ResourceUploadResp.fail("未知的后缀：" + suffixName);
        }
        relativePath
            .append(snowflake.nextIdStr())
            .append("_")
            .append(req.getHeader("username"))
            .append(".")
            .append(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]);

        String fullAbsPath = properties.path + relativePath;
        //String url = properties.nginxAddr + relativePath;
        file.transferTo(new File(fullAbsPath));
        if (SuffixSet.isPic(suffixName)) {
            //返回图片相对url，前端再拼接图片服务器的域名
            return ResourceUploadResp.picOk(relativePath.toString(), "😄", relativePath.toString());
        } else {
            return ResourceUploadResp.videoOk(relativePath.toString(), "");
        }
    }

    @PostMapping("/user/deleteResourceList")
    public RestResponse<Object> deletePic(@RequestBody List<String> list, HttpServletRequest req) {
        list.forEach((url) -> {
            String username = url.substring(url.indexOf("_") + 1, url.indexOf("."));
            String currentUser = req.getHeader("username");
            if (!currentUser.equals(username)) {
                log.error("用户" + currentUser + "企图删除" + url);
            }
            String suffixName = url.substring(url.lastIndexOf(".") + 1);
            String fullAbsPath = null;
            if (SuffixSet.isPic(suffixName)) {
                fullAbsPath = properties.path + properties.picPathPrefix + url.substring(url.lastIndexOf("/") + 1);
            }
            if (SuffixSet.isVideo(suffixName)) {
                fullAbsPath = properties.path + properties.videoPathPrefix + url.substring(url.lastIndexOf("/") + 1);
            }
            if (fullAbsPath == null) {
                log.error("未知的后缀：" + suffixName);
            } else {
                File file = new File(fullAbsPath);
                if (file.delete()) {
                    log.info("删除 " + fullAbsPath + " 成功");
                } else {
                    log.info("删除 " + fullAbsPath + " 失败");
                }
            }
        });
        return RestResponse.ok();
    }

    @PostMapping("/user/resetPassword")
    public RestResponse<Boolean> restResponse(@Valid @RequestBody ResetPasswordDTO dto) {
        if (!dto.getPassword().equals(dto.getRepeatedPassword())) {
            return RestResponse.fail();
        }
        return userService.updatePasswordByEmail(dto);
    }
}
