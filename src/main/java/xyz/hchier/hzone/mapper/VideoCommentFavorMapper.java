package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.VideoCommentFavor;
/**
 * @author Hchier
 */
@Mapper
public interface VideoCommentFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoCommentFavor record);

    VideoCommentFavor selectByPrimaryKey(Integer id);

    List<VideoCommentFavor> selectAll();

    int updateByPrimaryKey(VideoCommentFavor record);
}