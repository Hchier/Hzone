package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.BlogCommentFavor;

public interface BlogCommentFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogCommentFavor record);

    BlogCommentFavor selectByPrimaryKey(Integer id);

    List<BlogCommentFavor> selectAll();

    int updateByPrimaryKey(BlogCommentFavor record);
}