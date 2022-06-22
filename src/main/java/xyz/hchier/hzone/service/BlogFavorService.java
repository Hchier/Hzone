package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.BlogFavor;

import java.util.List;

/**
 * @author Hchier
 */
public interface BlogFavorService {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogFavor record);

    BlogFavor selectByPrimaryKey(Integer id);

    List<BlogFavor> selectAll();

    int updateByPrimaryKey(BlogFavor record);
}