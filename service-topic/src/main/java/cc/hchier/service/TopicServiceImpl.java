package cc.hchier.service;

import cc.hchier.enums.ResponseCode;
import cc.hchier.response.RestResponse;
import cc.hchier.configuration.Properties;
import cc.hchier.entity.Topic;
import cc.hchier.mapper.TopicMapper;
import cc.hchier.vo.TopicTinyVO;
import cc.hchier.vo.TopicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author by Hchier
 * @Date 2023/2/13 20:12
 */
@Service
@Slf4j
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
        if (topicMapper.selectCount(name) > 0) {
            return RestResponse.ok();
        }
        //todo
        if (topicMapper.insert(name, "") == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<TopicVO> get(String name, String currentUser) {
        TopicVO vo = topicMapper.selectVO(name, currentUser);
        return vo != null ? RestResponse.ok(vo) : RestResponse.build(ResponseCode.TOPIC_NOT_EXIST);
    }

    @Override
    public RestResponse<Object> incrReadNum(String name, int incr) {
        boolean existInRedis = false;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicTotalReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicTotalReadNumChart, name, incr);
        }

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicWeekReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicWeekReadNumChart, name, incr);
        }

        if (Boolean.TRUE.equals(redisTemplate.hasKey(properties.topicDayReadNumChart))) {
            existInRedis = true;
            redisTemplate.opsForZSet().incrementScore(properties.topicDayReadNumChart, name, incr);
        }

        if (existInRedis) {
            return RestResponse.ok();
        }

        if (topicMapper.incrNum(new Topic().setName(name).setTotalReadNum(incr).setWeekReadNum(incr).setDayReadNum(incr)) == 0) {
            return RestResponse.fail();
        }

        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrDiscussionNum(String name, int incr) {
        if (incr > 0 && add(name).getCode() != ResponseCode.OK.getCode()) {
            return RestResponse.fail();
        }
        if (topicMapper.incrNum(new Topic().setName(name).setDiscussionNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Object> incrFollowedNum(String name, int incr) {
        if (topicMapper.incrNum(new Topic().setName(name).setFollowedNum(incr)) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    /**
     * 将topicList放入topicTotalReadNumChart、topicWeekReadNumChart和topicDayReadNumChart中
     *
     * @param topicList 主题列表
     */
    private void loadReadNumToRedis(List<Topic> topicList) {
        for (Topic topic : topicList) {
            redisTemplate.opsForZSet().add(properties.topicTotalReadNumChart, topic.getName(), topic.getTotalReadNum());
            redisTemplate.opsForZSet().add(properties.topicWeekReadNumChart, topic.getName(), topic.getWeekReadNum());
            redisTemplate.opsForZSet().add(properties.topicDayReadNumChart, topic.getName(), topic.getDayReadNum());
        }
    }

    @Override
    public void reloadTopTopic() {
        redisTemplate.delete(properties.topicTotalReadNumChart);
        redisTemplate.delete(properties.topicWeekReadNumChart);
        redisTemplate.delete(properties.topicDayReadNumChart);

        List<Topic> topByTotalReadNum = topicMapper.selectTopByTotalReadNum();
        loadReadNumToRedis(topByTotalReadNum);

        List<Topic> topByWeekReadNum = topicMapper.selectTopByWeekReadNum();
        loadReadNumToRedis(topByWeekReadNum);


        List<Topic> topByDayReadNum = topicMapper.selectTopByDayReadNum();
        loadReadNumToRedis(topByDayReadNum);

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

    @Override
    public RestResponse<List<TopicTinyVO>> getReadNumChart(String type) {
        List<TopicTinyVO> list = new ArrayList<>();

        Set<ZSetOperations.TypedTuple<Object>> chart = null;
        switch (type) {
            case "total":
                chart = redisTemplate.opsForZSet().reverseRangeWithScores(properties.topicTotalReadNumChart, 0, 10);
                break;
            case "week":
                chart = redisTemplate.opsForZSet().reverseRangeWithScores(properties.topicWeekReadNumChart, 0, 10);
                break;
            case "day":
                chart = redisTemplate.opsForZSet().reverseRangeWithScores(properties.topicDayReadNumChart, 0, 10);
                break;
            default:
                log.error("位置的排行榜类型：" + type);
                break;
        }
        if (chart == null || chart.isEmpty()) {
            return RestResponse.ok();
        }
        for (ZSetOperations.TypedTuple<Object> item : chart) {
            String topicName = (String) item.getValue();
            Double readNumD = item.getScore();
            assert readNumD != null;
            int readNum = (int) Math.round(readNumD);
            list.add(
                new TopicTinyVO()
                    .setName(topicName)
                    .setReadNum(readNum)
            );
        }
        return RestResponse.ok(list);
    }
}
