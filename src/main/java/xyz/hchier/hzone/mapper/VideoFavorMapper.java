package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.VideoFavor;
/**
 * @author Hchier
 */
@Mapper
public interface VideoFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoFavor record);

    VideoFavor selectByPrimaryKey(Integer id);

    List<VideoFavor> selectAll();

    int updateByPrimaryKey(VideoFavor record);
}