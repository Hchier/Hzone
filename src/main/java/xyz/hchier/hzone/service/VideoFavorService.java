package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.VideoFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoFavorService {
    int deleteByPrimaryKey(Integer id);

    int insert(VideoFavor record);

    VideoFavor selectByPrimaryKey(Integer id);

    List<VideoFavor> selectAll();

    int updateByPrimaryKey(VideoFavor record);
}