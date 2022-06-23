package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface BlogFavorService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(BlogFavor record);

    RestResponse<BlogFavor> selectByPrimaryKey(Integer id);

    RestResponse<List<BlogFavor>> selectAll();

    RestResponse updateByPrimaryKey(BlogFavor record);
}