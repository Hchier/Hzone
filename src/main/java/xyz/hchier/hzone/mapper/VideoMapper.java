package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.Video;

/**
 * @author Hchier
 */
@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
}