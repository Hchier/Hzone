package cc.hchier.task;

import cc.hchier.configuration.Properties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * 准备工作
 *
 * @author by Hchier
 * @Date 2023/2/12 21:37
 */
@Component
public class PreparationTask implements ApplicationRunner {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Properties properties;

    public PreparationTask(RedisTemplate<String, Object> redisTemplate, Properties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) {
        //删除redis中与用户身份核验相关的key
        redisTemplate.delete(properties.zsetForTokenExpireTime);
        redisTemplate.delete(properties.hashForToken);

        //创建存放用户头像的目录
        File file1 = new File(properties.path + properties.avatarPathPrefix);
        boolean success1 = file1.mkdirs();

        //创建存放其它图片的目录
        File file2 = new File(properties.path + properties.picPathPrefix);
        boolean success2 = file2.mkdirs();

        File file3 = new File(properties.path + properties.videoPathPrefix);
        boolean success3 = file3.mkdirs();
    }

}
