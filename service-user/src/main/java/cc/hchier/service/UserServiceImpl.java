package cc.hchier.service;

import cc.hchier.dto.*;
import cc.hchier.enums.FollowType;
import cc.hchier.enums.ResponseCode;
import cc.hchier.response.RestResponse;
import cc.hchier.Utils;
import cc.hchier.configuration.Properties;
import cc.hchier.entity.User;
import cc.hchier.mapper.UserMapper;
import cc.hchier.vo.UserVO;
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
    private final FollowService followService;
    private final TalkService talkService;

    private final WsService wsService;

    public UserServiceImpl(RedisTemplate<String, Object> redisTemplate, UserMapper userMapper, Properties properties, FollowService followService, TalkService talkService, WsService wsService) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.properties = properties;
        this.followService = followService;
        this.talkService = talkService;
        this.wsService = wsService;
    }

    @Override
    public RestResponse<Object> register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepeatedPassword())) {
            return RestResponse.fail();
        }
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
            String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, expiredToken);
            talkService.closeChannel(username);
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
    public RestResponse<Object> incrBlogNum(String username, int incr) {
        if (userMapper.incrNum(new User().setUsername(username).setBlogNum(incr)) == 0) {
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

    @Override
    public RestResponse<Object> existUser(String username) {
        return userMapper.existUser(username) == 1 ? RestResponse.ok() : RestResponse.fail();
    }

    @Override
    public RestResponse<UserVO> getUserVO(String targetUser, String currentUser) {
        User user = userMapper.selectUser(targetUser);
        UserVO userVO = new UserVO()
            .setUsername(user.getUsername())
            .setSignature(user.getSignature())
            .setBlogNum(user.getBlogNum())
            .setFavorNum(user.getFavorNum())
            .setFavoredNum(user.getFavoredNum())
            .setFollowNum(user.getFollowNum())
            .setFollowedNum(user.getFollowedNum())
            .setFollowed(followService.existFollow(currentUser, targetUser, FollowType.USER.getCode()).getBody())
            .setIsOnline(wsService.isOnline(targetUser).getBody());
        return RestResponse.ok(userVO);
    }

    @Override
    public RestResponse<Boolean> updatePasswordByEmail(ResetPasswordDTO dto) {

        if (!dto.getAuthCode().equals(redisTemplate.opsForValue().get(dto.getEmail()))) {
            return RestResponse.build(ResponseCode.AUTH_FAIL);
        }
        dto.setPassword(Utils.md5Encode(dto.getPassword()));
        if (userMapper.updatePasswordByEmail(dto.getPassword(), dto.getEmail()) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }
}
