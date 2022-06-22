package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.Follow;
import xyz.hchier.hzone.service.FollowService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:25
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Follow record) {
        return 0;
    }

    @Override
    public Follow selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Follow> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Follow record) {
        return 0;
    }
}
