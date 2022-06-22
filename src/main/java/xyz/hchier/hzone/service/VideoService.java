package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.Video;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoService {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
}