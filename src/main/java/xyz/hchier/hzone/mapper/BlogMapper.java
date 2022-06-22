package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.Blog;

public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    Blog selectByPrimaryKey(Integer id);

    List<Blog> selectAll();

    int updateByPrimaryKey(Blog record);
}