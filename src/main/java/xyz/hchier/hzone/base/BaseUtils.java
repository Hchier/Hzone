package xyz.hchier.hzone.base;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 17:10
 */
@Component
public class BaseUtils {
    private static RedisTemplate redisTemplate;

    public BaseUtils(RedisTemplate redisTemplate) {
        BaseUtils.redisTemplate = redisTemplate;
    }

    public static String getCurrentUser(HttpServletRequest request) {
        return (String) redisTemplate.opsForHash().get(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), request.getSession().getId());
    }
}
