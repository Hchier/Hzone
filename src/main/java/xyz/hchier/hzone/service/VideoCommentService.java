package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.VideoComment;

import java.util.List;

/**
 * @author Hchier
 */
public interface VideoCommentService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(VideoComment record);

    RestResponse<VideoComment> selectByPrimaryKey(Integer id);

    RestResponse<List<VideoComment>> selectAll();

    RestResponse updateByPrimaryKey(VideoComment record);
}