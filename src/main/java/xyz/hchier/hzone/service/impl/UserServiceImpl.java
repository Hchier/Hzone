package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.User;
import xyz.hchier.hzone.service.UserService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:26
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public int deleteByPrimaryKey(String username) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(String username) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
