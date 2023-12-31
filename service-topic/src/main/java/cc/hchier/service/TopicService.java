package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.vo.TopicTinyVO;
import cc.hchier.vo.TopicVO;

import java.util.List;

/**
 * @author hchier
 */
public interface TopicService {
    /**
     * 新增话题
     * 先判断是否存在，若存在，则直接返回ok。若不存在，则创建
     *
     * @param name 名字
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> add(String name);

    /**
     * 查找话题
     * 存在返回ok，否则返回fail
     *
     * @param name 名字
     * @param currentUser 当前用户
     * @return {@link RestResponse}
     */
    RestResponse<TopicVO> get(String name, String currentUser);

    /**
     * 阅读量+incr
     * 如果话题上总榜、周榜或日榜了，则只更新redis中的数据，否则只更新MySQL中的数据。
     *
     * @param name topic_name
     * @param incr 增量
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> incrReadNum(String name,int incr);

    /**
     * 讨论量+incr
     * 若话题不存在，则先新增
     *
     * @param name 名字
     * @param incr 增量
     * @return {@link RestResponse}
     */
    RestResponse<Object> incrDiscussionNum(String name,int incr);

    /**
     * 关注量+incr
     *
     * @param name 名字
     * @param incr 增量
     * @return {@link RestResponse}
     */
    RestResponse<Object> incrFollowedNum(String name,int incr);


    /**
     * 重新加载话题热搜
     * 重新从MySQL中拿数据，放入redis中。
     * 只要上了总榜、周榜或日榜，就将其放入topicTotalReadNumChart、topicWeekReadNumChart和topicDayReadNumChart中
     */
    void reloadTopTopic();

    /**
     * 更新阅读量
     * 将redis中的数据写入MySQL
     */
    void updateReadNum();


    /**
     * 返回总榜、周榜或日榜
     *
     * @param type 类型
     * @return {@link RestResponse}<{@link List}<{@link TopicTinyVO}>>
     */
    RestResponse<List<TopicTinyVO>> getReadNumChart(String type);

}
