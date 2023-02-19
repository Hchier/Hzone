package cc.hchier.service;

import cc.hchier.RestResponse;

/**
 * @author hchier
 */
public interface TopicService {
    /**
     * 添加
     * 新增话题
     * 先判断是否存在，若存在，则直接返回ok
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
     * @return {@link RestResponse}
     */
    RestResponse<Object> get(String name);

    /**
     * 阅读量+1
     * 如果话题上总榜、周榜或日榜了，则只更新redis中的数据，否则只更新MySQL中的数据。
     *
     * @param name topic_name
     * @return {@link RestResponse}
     */
    RestResponse<Object> incrReadNum(String name);

    /**
     * 讨论量+1
     *
     * @param name 名字
     * @return {@link RestResponse}
     */
    RestResponse<Object> incrDiscussionNum(String name);

    /**
     * 关注量+1
     *
     * @param name 名字
     * @return {@link RestResponse}
     */
    RestResponse<Object> incrFollowedNum(String name);


    /**
     * 重新加载话题热搜
     * 重新从MySQL中拿数据，放入redis中
     */
    void reloadTopTopic();

    /**
     * 更新阅读量
     * 将redis中的数据写入MySQL
     */
    void updateReadNum();

}
