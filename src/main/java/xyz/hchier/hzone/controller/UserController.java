package xyz.hchier.hzone.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author by Hchier
 * @Date 2022/6/23 13:39
 */
@RestController("/user")
public class UserController {
    @PostMapping("/login")
    public RestResponse login(@RequestBody User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.getId()
    }
}
