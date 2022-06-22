package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.VideoCommentFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoCommentFavorService {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoCommentFavor record);

    VideoCommentFavor selectByPrimaryKey(Integer id);

    List<VideoCommentFavor> selectAll();

    int updateByPrimaryKey(VideoCommentFavor record);
}