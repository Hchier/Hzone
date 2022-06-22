package xyz.hchier.hzone.service;


import xyz.hchier.hzone.entity.BlogCommentFavor;

import java.util.List;


/**
 * @author Hchier
 */
public interface BlogCommentFavorService {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogCommentFavor record);

    BlogCommentFavor selectByPrimaryKey(Integer id);

    List<BlogCommentFavor> selectAll();

    int updateByPrimaryKey(BlogCommentFavor record);
}