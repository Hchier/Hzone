package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.VideoComment;
/**
 * @author Hchier
 */
@Mapper
public interface VideoCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoComment record);

    VideoComment selectByPrimaryKey(Integer id);

    List<VideoComment> selectAll();

    int updateByPrimaryKey(VideoComment record);
}