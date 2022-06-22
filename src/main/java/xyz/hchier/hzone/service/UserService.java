package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.User;

import java.util.List;

/**
 * @author Hchier
 */
public interface UserService {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    User selectByPrimaryKey(String username);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}