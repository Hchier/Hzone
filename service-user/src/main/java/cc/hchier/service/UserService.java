package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.UserEmailUpdateDTO;
import cc.hchier.dto.UserLoginDTO;
import cc.hchier.dto.UserPwdUpdateDTO;
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
    RestResponse<Object> register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录。密码错误或账号被ban均无法登录。
     *
     * @param userLoginDTO 用户登录dto
     * @return {@link RestResponse}
     */
    RestResponse<Object> login(UserLoginDTO userLoginDTO);

    /**
     * 2件事。
     * 1，将<token, id>放入tokens(Hash)中。
     * 2，将<token, expireTime>放入expireTime(Zset)中
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
    RestResponse<Object> close(String username);


    /**
     * 点赞量 + incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> incrFavorNum(String username, int incr);

    /**
     * 被点赞量 +incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> incrFavoredNum(String username, int incr);

    /**
     * 偶像数量 + incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> incrFollowNum(String username, int incr);


    /**
     * 粉丝数 + incr
     *
     * @param username 用户名
     * @param incr     增量
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> incrFollowedNum(String username, int incr);

    /**
     * 重置pwd
     *
     * @param userPwdUpdateDTO 用户pwd重置dto
     * @return {@link RestResponse}
     */
    RestResponse<Object> updatePwd(UserPwdUpdateDTO userPwdUpdateDTO);

    /**
     * 查找用户的email
     *
     * @param username 用户名
     * @return {@link RestResponse}
     */
    RestResponse<Object> getEmailOfCurrentUser(String username);

    /**
     * 更新email
     *
     * @param dto dto
     * @return {@link RestResponse}
     */
    RestResponse<Object> updateEmail(UserEmailUpdateDTO dto);

    /**
     * 判断是否存在用户
     *
     * @param username 用户名
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> existUser(String username);
}
