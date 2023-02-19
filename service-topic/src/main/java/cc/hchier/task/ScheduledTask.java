package cc.hchier.task;

import cc.hchier.configuration.Properties;
import cc.hchier.service.TopicService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author by Hchier
 * @Date 2023/2/19 15:41
 */
@Component
public class ScheduledTask {
    private final TopicService topicService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Properties properties;

    public ScheduledTask(TopicService topicService, RedisTemplate<String, Object> redisTemplate, Properties properties) {
        this.topicService = topicService;
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    /**
     * 定时刷新刷新热搜
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void refreshTopTopic() {
        topicService.updateReadNum();
        topicService.reloadTopTopic();
    }

    /**
     * 重置话题日榜
     */
    @Scheduled(cron = "55 59 23 * * ?")
    public void resetTopicDayReadNum() {
        Set<Object> names = redisTemplate.opsForZSet().range(properties.topicDayReadNumChart, 0, -1);
        if (names == null || names.isEmpty()) return;
        for (Object topicName : names) {
            redisTemplate.opsForZSet().add(properties.topicDayReadNumChart, topicName, 0);
        }
    }

    /**
     * 重置话题周榜
     */
    @Scheduled(cron = "55 59 23 ? * SUN")
    public void resetTopicWeekReadNum() {
        Set<Object> names = redisTemplate.opsForZSet().range(properties.topicWeekReadNumChart, 0, -1);
        if (names == null || names.isEmpty()) return;
        for (Object topicName : names) {
            redisTemplate.opsForZSet().add(properties.topicWeekReadNumChart, topicName, 0);
        }
    }
}
