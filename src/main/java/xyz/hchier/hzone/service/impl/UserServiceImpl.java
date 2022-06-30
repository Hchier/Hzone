package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.Utils.Md5Util;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.mapper.UserMapper;
import xyz.hchier.hzone.service.RedisService;
import xyz.hchier.hzone.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 12:34
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RedisService redisService;

    public UserServiceImpl(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }


    /**
     * 注册用户
     *
     * @param user 用户
     * @return if userNotExist, register and return RestResponse.ok(), else return RestResponse.fail();
     */
    @Override
    public RestResponse register(User user) {
        user.setPassword(Md5Util.encode(user.getPassword()));
        int res;
        try {
            res = userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.fail(ResponseCode.REGISTER_FAIL.getCode(), ResponseCode.REGISTER_FAIL.getMessage());
        }
        return res == 1 ? RestResponse.ok() : RestResponse.fail(ResponseCode.ERROR_UNKNOWN.getCode(), ResponseCode.ERROR_UNKNOWN.getMessage());
    }

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return if userNotExist, return RestResponse.ok(), else return RestResponse.fail();
     */
    @Override
    public RestResponse notExist(String username) {
        String password = userMapper.selectPasswordByUsername(username);
        return password == null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.USER_ALREADY_EXIST.getCode(), ResponseCode.USER_ALREADY_EXIST.getMessage());
    }

    /**
     * 登录
     *
     * @param user      用户
     * @param sessionId 会话id
     * @return username与password匹配时，将sessionId, username存入redis、用zset记录过期时间、加载用户点赞信息并返回RestResponse.ok()，
     * 否则返回RestResponse.fail();
     */
    @Override
    public RestResponse login(User user, String sessionId) {
        String password = userMapper.selectPasswordByUsername(user.getUsername());
        if (!Md5Util.encode(user.getPassword()).equals(password)) {
            return RestResponse.fail(ResponseCode.AUTH_FAIL.getCode(), ResponseCode.AUTH_FAIL.getMessage());
        }
        redisService.addSessionIdAndUsername(sessionId, user.getUsername());
        redisService.loadBlogFavorByUsername(user.getUsername());
        redisService.writeBlogFavorOfUser(user.getUsername());

        redisService.writeBlogFavorNum();
        return RestResponse.ok();
    }

    @Override
    public RestResponse logout(String sessionId) {
        redisService.processExpiredSession(sessionId);
        return RestResponse.ok();
    }


}
