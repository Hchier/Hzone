package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.BlogCommentFavor;

@Mapper
public interface BlogCommentFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogCommentFavor record);

    BlogCommentFavor selectByPrimaryKey(Integer id);

    List<BlogCommentFavor> selectAll();

    int updateByPrimaryKey(BlogCommentFavor record);
}