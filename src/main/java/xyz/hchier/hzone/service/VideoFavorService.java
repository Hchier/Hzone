package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.VideoFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoFavorService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(VideoFavor record);

    RestResponse<VideoFavor> selectByPrimaryKey(Integer id);

    RestResponse<List<VideoFavor>> selectAll();

    RestResponse updateByPrimaryKey(VideoFavor record);
}