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

    @Scheduled(fixedDelay = 60000)
    public void removeExpiredSessions(){
        redisService.removeExpiredSessionIds();
    }
}
