package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.Video;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
}