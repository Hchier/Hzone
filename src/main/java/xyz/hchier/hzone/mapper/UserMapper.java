package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    User selectByPrimaryKey(String username);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}