package xyz.hchier.hzone.service;


import xyz.hchier.hzone.entity.Blog;

import java.util.List;


/**
 * @author Hchier
 */
public interface BlogService {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    Blog selectByPrimaryKey(Integer id);

    List<Blog> selectAll();

    int updateByPrimaryKey(Blog record);
}