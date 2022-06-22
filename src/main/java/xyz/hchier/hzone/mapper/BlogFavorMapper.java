package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.BlogFavor;

public interface BlogFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogFavor record);

    BlogFavor selectByPrimaryKey(Integer id);

    List<BlogFavor> selectAll();

    int updateByPrimaryKey(BlogFavor record);
}