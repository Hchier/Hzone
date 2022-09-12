package xyz.hchier.hzone.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.User;
/**
 * @author Hchier
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    User selectByPrimaryKey(String username);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    String selectPasswordByUsername(String username);

    String selectEmailByBlogId(Integer id);
}