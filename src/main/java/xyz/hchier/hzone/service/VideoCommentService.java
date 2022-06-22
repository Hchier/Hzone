package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.VideoComment;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoCommentService {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoComment record);

    VideoComment selectByPrimaryKey(Integer id);

    List<VideoComment> selectAll();

    int updateByPrimaryKey(VideoComment record);
}