package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.BlogComment;

import java.util.List;

/**
 * @author Hchier
 */

public interface BlogCommentService {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogComment record);

    BlogComment selectByPrimaryKey(Integer id);

    List<BlogComment> selectAll();

    int updateByPrimaryKey(BlogComment record);
}