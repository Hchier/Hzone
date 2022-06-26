package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.Blog;

/**
 * @author Hchier
 */
@Mapper
public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    Blog selectByPrimaryKey(Integer id);

    List<Blog> selectAllIdAndUsername();

    int updateByPrimaryKey(Blog record);

    String selectUsernameById(Integer id);


}