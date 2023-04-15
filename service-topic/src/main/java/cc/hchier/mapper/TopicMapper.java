package cc.hchier.mapper;

import cc.hchier.entity.Topic;
import cc.hchier.vo.TopicVO;
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
     * @param name   话题名
     * @param picUrl picUrl
     * @return int
     */
    int insert(@Param("name") String name, @Param("picUrl") String picUrl);

    /**
     * 根据name查找话题
     *
     * @param name 名字
     * @return {@link Topic}
     */
    int selectCount(@Param("name") String name);


    /**
     * get vo
     *
     * @param name        名字
     * @param currentUser 当前用户
     * @return {@link Topic}
     */
    TopicVO selectVO(@Param("name") String name, @Param("currentUser") String currentUser);

    /**
     * 各种num + 1
     * 不为null时生效
     *
     * @param topic 主题
     * @return int
     */
    int incrNum(Topic topic);

    /**
     * 根据总阅读量降序排序，查找最高的10个
     *
     * @return {@link List}<{@link Topic}>
     */
    List<Topic> selectTopByTotalReadNum();

    /**
     * 根据周阅读量降序排序，查找最高的10个
     *
     * @return {@link List}<{@link Topic}>
     */
    List<Topic> selectTopByWeekReadNum();

    /**
     * 根据日阅读量降序排序，查找最高的10个
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