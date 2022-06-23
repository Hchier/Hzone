package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;

import java.util.List;

/**
 * @author Hchier
 */

public interface BlogCommentService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(BlogComment record);

    RestResponse<BlogComment> selectByPrimaryKey(Integer id);

    RestResponse<List<BlogComment>> selectAll();

    RestResponse updateByPrimaryKey(BlogComment record);
}