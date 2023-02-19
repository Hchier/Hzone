package cc.hchier.task;

import cc.hchier.configuration.Properties;
import cc.hchier.service.TopicService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author by Hchier
 * @Date 2023/2/19 15:41
 */
@Component
public class PreparationTask implements ApplicationRunner {
    private final TopicService topicService;

    public PreparationTask(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public void run(ApplicationArguments args) {
        topicService.reloadTopTopic();
    }
}

