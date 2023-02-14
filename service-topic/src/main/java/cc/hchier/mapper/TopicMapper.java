package cc.hchier.mapper;

import cc.hchier.entity.Topic;
import org.apache.ibatis.annotations.Mapper;

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
     *  根据name查找话题
     *
     * @param name 名字
     * @return {@link Topic}
     */
    Topic selectByName(String name);

//
//    List<Topic> selectAll();
//
//    int updateByPrimaryKey(Topic record);
}