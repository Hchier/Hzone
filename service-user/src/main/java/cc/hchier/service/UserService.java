package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.UserLoginDTO;
import cc.hchier.dto.UserRegisterDTO;

/**
 * @author by Hchier
 * @Date 2023/2/12 11:57
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册dto
     * @return {@link RestResponse}
     */
    RestResponse register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录。密码错误或账号被ban均无法登录。
     *
     * @param userLoginDTO 用户登录dto
     * @return {@link RestResponse}
     */
    RestResponse login(UserLoginDTO userLoginDTO);

    /**
     *  2件事。
     *  1，将<token, id>放入tokens(Hash)中。
     *  2，将<token, expireTime>放入expireTime(Zset)中
     *
     * @param token    令牌
     * @param username 用户名
     */
    void setToken(String token, String username);

    /**
     * 移除过期的token。2件事：1，从tokens(Hash)中移除过期的<token, id>。 2，从expireTime(Zset)中移除过期的<token, expireTime>
     */
    void removeExpiredTokens();


    /**
     * ban
     *
     * @param username 用户名
     * @return {@link RestResponse}
     */
    RestResponse close(String username);
}
