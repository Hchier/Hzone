package cc.hchier.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 *
 * @author by Hchier
 * @Date 2023/2/12 21:29
 */
@Component
public class ScheduledTask {
    private final UserService userService;

    public ScheduledTask(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void removeExpiredTokens() {
        userService.removeExpiredTokens();
    }
}
