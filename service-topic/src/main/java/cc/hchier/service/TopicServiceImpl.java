package cc.hchier.service;

import cc.hchier.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.configuration.Properties;
import cc.hchier.entity.Topic;
import cc.hchier.mapper.TopicMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author by Hchier
 * @Date 2023/2/13 20:12
 */
@Service
public class TopicServiceImpl implements TopicService {
    private final TopicMapper topicMapper;
    private final Properties properties;

    private final RedisTemplate<String, Object> redisTemplate;

    public TopicServiceImpl(TopicMapper topicMapper, Properties properties, RedisTemplate<String, Object> redisTemplate) {
        this.topicMapper = topicMapper;
        this.properties = properties;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RestResponse<Object> add(String name) {
        if (topicMapper.selectByName(name) != null) {
            return RestResponse.ok();
        }
        if (topicMapper.insert(name) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> get(String name) {
        Topic topic = topicMapper.selectByName(name);
        return topic != null ? RestResponse.ok(topic) : RestResponse.build(ResponseCode.TOPIC_NOT_EXIST);
    }

    @Override
    public RestResponse<Object> incrReadNum(String name) {
        boolean existInRedis = false;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicTotalReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicTotalReadNumChart, name, 1);
        }

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicWeekReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicWeekReadNumChart, name, 1);
        }

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicDayReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicDayReadNumChart, name, 1);
        }

        if (existInRedis) {
            return RestResponse.ok();
        }

        if (topicMapper.incrNum(new Topic().setName(name).setTotalReadNum(6).setWeekReadNum(6).setDayReadNum(6)) == 0) {
            return RestResponse.fail();
        }

        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrDiscussionNum(String name) {
        if (topicMapper.incrNum(new Topic().setName(name).setDiscussionNum(6)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFollowedNum(String name) {
        if (topicMapper.incrNum(new Topic().setName(name).setFollowedNum(6)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public void reloadTopTopic() {
        redisTemplate.delete(properties.topicTotalReadNumChart);
        redisTemplate.delete(properties.topicWeekReadNumChart);
        redisTemplate.delete(properties.topicDayReadNumChart);

        List<Topic> topByTotalReadNum = topicMapper.selectTopByTotalReadNum();
        for (Topic topic : topByTotalReadNum) {
            redisTemplate.opsForZSet().add(properties.topicTotalReadNumChart, topic.getName(), topic.getTotalReadNum());
        }

        List<Topic> topByWeekReadNum = topicMapper.selectTopByWeekReadNum();
        for (Topic topic : topByWeekReadNum) {
            redisTemplate.opsForZSet().add(properties.topicWeekReadNumChart, topic.getName(), topic.getWeekReadNum());
        }

        List<Topic> topByDayReadNum = topicMapper.selectTopByDayReadNum();
        for (Topic topic : topByDayReadNum) {
            redisTemplate.opsForZSet().add(properties.topicDayReadNumChart, topic.getName(), topic.getDayReadNum());
        }
    }

    @Override
    public void updateReadNum() {
        Set<ZSetOperations.TypedTuple<Object>> totalReadNumSet = redisTemplate.opsForZSet().rangeWithScores(properties.topicTotalReadNumChart, 0, -1);
        if (totalReadNumSet != null && !totalReadNumSet.isEmpty()) {
            List<Topic> list = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Object> item : totalReadNumSet) {
                String topicName = (String) item.getValue();
                Double readNumD = item.getScore();
                assert readNumD != null;
                int readNum = (int) Math.round(readNumD);
                list.add(new Topic().setName(topicName).setTotalReadNum(readNum));
            }
            topicMapper.updateTotalReadNum(list);
        }

        Set<ZSetOperations.TypedTuple<Object>> weekReadNumSet = redisTemplate.opsForZSet().rangeWithScores(properties.topicWeekReadNumChart, 0, -1);
        if (weekReadNumSet != null && !weekReadNumSet.isEmpty()) {
            List<Topic> list = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Object> item : weekReadNumSet) {
                String topicName = (String) item.getValue();
                Double readNumD = item.getScore();
                assert readNumD != null;
                int readNum = (int) Math.round(readNumD);
                list.add(new Topic().setName(topicName).setWeekReadNum(readNum));
            }
            topicMapper.updateWeekReadNum(list);
        }

        Set<ZSetOperations.TypedTuple<Object>> dayReadNumSet = redisTemplate.opsForZSet().rangeWithScores(properties.topicDayReadNumChart, 0, -1);
        if (dayReadNumSet != null && !dayReadNumSet.isEmpty()) {
            List<Topic> list = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Object> item : dayReadNumSet) {
                String topicName = (String) item.getValue();
                Double readNumD = item.getScore();
                assert readNumD != null;
                int readNum = (int) Math.round(readNumD);
                list.add(new Topic().setName(topicName).setDayReadNum(readNum));
            }
            topicMapper.updateDayReadNum(list);
        }
    }
}
