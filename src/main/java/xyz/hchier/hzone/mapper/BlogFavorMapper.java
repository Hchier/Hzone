package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.hchier.hzone.entity.BlogFavor;

/**
 * @author Hchier
 */
@Mapper
public interface BlogFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogFavor record);

    BlogFavor selectByPrimaryKey(Integer id);

    List<BlogFavor> selectFavorInfo(@Param("username") String username);

    int updateByPrimaryKey(BlogFavor record);



}