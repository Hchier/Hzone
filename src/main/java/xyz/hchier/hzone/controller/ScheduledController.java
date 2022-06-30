package xyz.hchier.hzone.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.hchier.hzone.service.RedisService;

/**
 * @author by Hchier
 * @Date 2022/6/26 9:58
 */
@Component
public class ScheduledController {
    private RedisService redisService;

    public ScheduledController(RedisService redisService) {
        this.redisService = redisService;
    }


    /**
     * 处理过期的会话
     * 移除过期的session
     */
    @Scheduled(fixedRate = 30000)
    public void processExpiredSessions(){
        redisService.checkAndProcessExpiredSessions();
    }

    @Scheduled(fixedDelay = 30000)
    public void writeBlogFavorNum(){
        redisService.writeBlogFavorNum();
    }
}
