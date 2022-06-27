package xyz.hchier.hzone.base;

import org.springframework.stereotype.Component;
import xyz.hchier.hzone.service.RedisService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/6/23 17:10
 */
@Component
public class BaseUtils {
    private static RedisService redisService;

    public BaseUtils(RedisService redisService) {
        BaseUtils.redisService = redisService;
    }

    public static String getCurrentUser(HttpServletRequest request) {
        return redisService.getUsernameBySessionId(request.getSession().getId());
    }
}
