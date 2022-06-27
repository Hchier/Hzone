package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.hchier.hzone.entity.Blog;

/**
 * @author Hchier
 */
@Mapper
public interface BlogMapper {
    int delete(@Param("id") Integer id, @Param("username") String username);

    int insert(Blog record);

    Blog selectByPrimaryKey(Integer id);

    List<Blog> selectAllIdAndUsername();

    int updateByPrimaryKey(Blog record);

    String selectUsernameById(Integer id);

    Integer selectBlogFavorNumById(Integer id);

    int multiUpdateBlogFavorNum(List<Blog> blogList);
}