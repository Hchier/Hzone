package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.VideoCommentFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoCommentFavorService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(VideoCommentFavor record);

    RestResponse<VideoCommentFavor> selectByPrimaryKey(Integer id);

    RestResponse<List<VideoCommentFavor>> selectAll();

    RestResponse updateByPrimaryKey(VideoCommentFavor record);
}