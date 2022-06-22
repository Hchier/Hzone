package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.VideoComment;

public interface VideoCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoComment record);

    VideoComment selectByPrimaryKey(Integer id);

    List<VideoComment> selectAll();

    int updateByPrimaryKey(VideoComment record);
}