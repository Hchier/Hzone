package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.entity.User;

import java.util.List;

/**
 * @author Hchier
 */
public interface UserService {
    /**
     * 注册
     *
     * @param user 用户
     * @return {@link RestResponse}
     */
    @TestPass
    RestResponse register(User user);


    /**
     * 用户是否存在
     *
     * @param username 用户名
     * @return {@link RestResponse}
     */
    @TestPass
    RestResponse notExist(String username);

    @TestPass
    RestResponse login(User user, String sessionId) throws InterruptedException;

}