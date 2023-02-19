package cc.hchier.mapper;

import cc.hchier.entity.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface TopicMapper {

    /**
     * 插入话题
     *
     * @param name 话题名
     * @return int
     */
    int insert(String name);

    /**
     * 根据name查找话题
     *
     * @param name 名字
     * @return {@link Topic}
     */
    Topic selectByName(String name);

    /**
     * 各种num + 1
     * 不为null时生效
     *
     * @param topic 主题
     * @return int
     */
    int incrNum(Topic topic);

    /**
     * 根据总阅读量降序排序，查找最高的20个
     *
     * @return {@link List}<{@link Topic}>
     */
    List<Topic> selectTopByTotalReadNum();

    /**
     * 根据周阅读量降序排序，查找最高的20个
     *
     * @return {@link List}<{@link Topic}>
     */
    List<Topic> selectTopByWeekReadNum();

    /**
     * 根据日阅读量降序排序，查找最高的20个
     *
     * @return {@link List}<{@link Topic}>
     */
    List<Topic> selectTopByDayReadNum();

    /**
     * 批量更新总榜阅读量
     *
     * @param topicList 主题列表
     * @return int
     */
    int updateTotalReadNum(@Param("topicList") List<Topic> topicList);

    /**
     * 批量更新周榜阅读量
     *
     * @param topicList 主题列表
     * @return int
     */
    int updateWeekReadNum(@Param("topicList") List<Topic> topicList);

    /**
     * 批量更新日榜阅读量
     *
     * @param topicList 主题列表
     * @return int
     */
    int updateDayReadNum(@Param("topicList") List<Topic> topicList);

//
//    List<Topic> selectAll();
//
//    int updateByPrimaryKey(Topic record);
}