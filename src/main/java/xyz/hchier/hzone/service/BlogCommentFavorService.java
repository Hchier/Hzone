package xyz.hchier.hzone.service;


import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogCommentFavor;

import java.util.List;


/**
 * @author Hchier
 */
public interface BlogCommentFavorService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(BlogCommentFavor record);

    RestResponse<BlogCommentFavor> selectByPrimaryKey(Integer id);

    RestResponse<List<BlogCommentFavor>> selectAll();

    RestResponse updateByPrimaryKey(BlogCommentFavor record);
}