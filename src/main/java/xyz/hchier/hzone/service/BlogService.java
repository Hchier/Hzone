package xyz.hchier.hzone.service;


import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Blog;

import java.util.List;


/**
 * @author Hchier
 */
public interface BlogService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(Blog record);

    RestResponse<Blog> selectByPrimaryKey(Integer id);

    RestResponse<List<Blog>> selectAll();

    RestResponse updateByPrimaryKey(Blog record);
}