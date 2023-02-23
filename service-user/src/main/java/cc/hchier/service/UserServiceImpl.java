package cc.hchier.service;

import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.Utils;
import cc.hchier.configuration.Properties;
import cc.hchier.dto.UserEmailUpdateDTO;
import cc.hchier.dto.UserLoginDTO;
import cc.hchier.dto.UserPwdUpdateDTO;
import cc.hchier.dto.UserRegisterDTO;
import cc.hchier.entity.User;
import cc.hchier.mapper.UserMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author by Hchier
 * @Date 2023/2/12 11:57
 */
@Service
public class UserServiceImpl implements UserService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserMapper userMapper;
    private final Properties properties;

    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate, UserMapper userMapper, Properties properties) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.properties = properties;
    }

    @Override
    public RestResponse<Object> register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getAuthCode().equals(redisTemplate.opsForValue().get(userRegisterDTO.getEmail()))) {
            return RestResponse.build(ResponseCode.AUTH_FAIL);
        }
        userRegisterDTO.setPassword(Utils.md5Encode(userRegisterDTO.getPassword()));
        if (userMapper.insert(userRegisterDTO) == 0) {
            return RestResponse.fail();
        }
        redisTemplate.delete(userRegisterDTO.getEmail());
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> login(UserLoginDTO userLoginDTO) {
        if (!Utils.md5Encode(userLoginDTO.getPassword()).equals(userMapper.selectPasswordByUsername(userLoginDTO.getUsername()))) {
            return RestResponse.build(ResponseCode.AUTH_FAIL);
        }
        return RestResponse.ok();
    }

    @Override
    public void setToken(String token, String username) {
        redisTemplate.opsForHash().put(properties.hashForToken, token, username);
        redisTemplate.opsForZSet().add(
            properties.zsetForTokenExpireTime,
            token,
            System.currentTimeMillis() + (long) properties.tokenLifeCycle * 60 * 1000);
    }

    @Override
    public void removeExpiredTokens() {
        Set<Object> expiredTokens = redisTemplate.opsForZSet().rangeByScore(properties.zsetForTokenExpireTime, 0, System.currentTimeMillis());
        if (expiredTokens == null || expiredTokens.isEmpty()) {
            return;
        }
        redisTemplate.opsForZSet().removeRangeByScore(properties.zsetForTokenExpireTime, 0, System.currentTimeMillis());
        for (Object expiredToken : expiredTokens) {
            redisTemplate.opsForHash().delete(properties.hashForToken, expiredToken);
        }
    }

    @Override
    public RestResponse<Object> close(String username) {
        if (userMapper.update(new User().setUsername(username).setClosed(true)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFavorNum(String username, int incr) {
        if (userMapper.incrNum(new User().setUsername(username).setFavorNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFavoredNum(String username, int incr) {
        if (userMapper.incrNum(new User().setUsername(username).setFavoredNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFollowNum(String username, int incr) {
        if (userMapper.incrNum(new User().setUsername(username).setFollowNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFollowedNum(String username, int incr) {
        if (userMapper.incrNum(new User().setUsername(username).setFollowedNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> updatePwd(UserPwdUpdateDTO dto) {
        if (!dto.getAuthCode().equals(redisTemplate.opsForValue().get(dto.getEmail()))) {
            return RestResponse.build(ResponseCode.AUTH_FAIL);
        }
        if (userMapper.update(
            new User()
                .setUsername(dto.getUsername())
                .setPassword(Utils.md5Encode(dto.getPassword()))) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> getEmailOfCurrentUser(String username) {
        return RestResponse.ok(userMapper.selectEmailByUsername(username));
    }

    @Override
    public RestResponse<Object> updateEmail(UserEmailUpdateDTO dto) {
        if (!dto.getAuthCode().equals(redisTemplate.opsForValue().get(dto.getNewEmail()))) {
            return RestResponse.build(ResponseCode.AUTH_FAIL);
        }
        if (userMapper.update(new User().setUsername(dto.getUsername()).setEmail(dto.getNewEmail())) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }
}
