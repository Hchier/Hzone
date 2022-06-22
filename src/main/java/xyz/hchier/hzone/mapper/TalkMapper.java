package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.Talk;
/**
 * @author Hchier
 */
@Mapper
public interface TalkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Talk record);

    Talk selectByPrimaryKey(Integer id);

    List<Talk> selectAll();

    int updateByPrimaryKey(Talk record);
}