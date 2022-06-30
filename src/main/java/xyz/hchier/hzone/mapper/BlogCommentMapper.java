package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.hchier.hzone.entity.BlogComment;

/**
 * @author Hchier
 */
@Mapper
public interface BlogCommentMapper {
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("username") String username, @Param("blogId") int blogId);

    int insert(BlogComment record);

    BlogComment selectByPrimaryKey(Integer id);

    List<BlogComment> selectAll();

    int updateByPrimaryKey(BlogComment record);

    List<BlogComment> getLimit(@Param("blogId") Integer blogId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

}