package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.BlogComment;

public interface BlogCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogComment record);

    BlogComment selectByPrimaryKey(Integer id);

    List<BlogComment> selectAll();

    int updateByPrimaryKey(BlogComment record);
}