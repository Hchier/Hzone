package cc.hchier.service;

import cc.hchier.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.entity.Topic;
import cc.hchier.mapper.TopicMapper;
import org.springframework.stereotype.Service;

/**
 * @author by Hchier
 * @Date 2023/2/13 20:12
 */
@Service
public class TopicServiceImpl implements TopicService {
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    @Override
    public RestResponse add(String name) {
        if (topicMapper.selectByName(name) != null) {
            return RestResponse.ok();
        }
        if (topicMapper.insert(name) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse get(String name) {
        Topic topic = topicMapper.selectByName(name);
        return topic != null ? RestResponse.ok(topic) : RestResponse.build(ResponseCode.TOPIC_NOT_EXIST);
    }
}
