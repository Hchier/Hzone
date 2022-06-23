package xyz.hchier.hzone.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.mapper.UserMapper;
import xyz.hchier.hzone.service.UserService;

import java.util.concurrent.TimeUnit;

/**
 * @author by Hchier
 * @Date 2022/6/23 12:34
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RedisTemplate redisTemplate;

    public UserServiceImpl(UserMapper userMapper, RedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 注册用户
     *
     * @param user 用户
     * @return if userNotExist, return RestResponse.ok(), else return RestResponse.fail();
     */
    @Override
    public RestResponse register(User user) {
        int res;
        try {
            res = userMapper.insert(user);
        } catch (Exception e) {
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

    @Override
    public RestResponse login(User user, String sessionId) throws InterruptedException {
        String password = userMapper.selectPasswordByUsername(user.getUsername());
        if (!user.getPassword().equals(password)) {
            return RestResponse.fail(ResponseCode.AUTH_FAIL.getCode(), ResponseCode.AUTH_FAIL.getMessage());
        }
        redisTemplate.opsForValue().set(sessionId, user.getUsername(), 60, TimeUnit.SECONDS);
        return RestResponse.ok();
    }
}
