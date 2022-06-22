package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.VideoCommentFavor;

public interface VideoCommentFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoCommentFavor record);

    VideoCommentFavor selectByPrimaryKey(Integer id);

    List<VideoCommentFavor> selectAll();

    int updateByPrimaryKey(VideoCommentFavor record);
}