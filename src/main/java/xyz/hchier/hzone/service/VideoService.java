package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Video;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(Video record);

    RestResponse<Video> selectByPrimaryKey(Integer id);

    RestResponse<List<Video>> selectAll();

    RestResponse updateByPrimaryKey(Video record);
}