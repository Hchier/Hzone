package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.Follow;

import java.util.List;

/**
 * @author Hchier
 */
public interface FollowService {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    Follow selectByPrimaryKey(Integer id);

    List<Follow> selectAll();

    int updateByPrimaryKey(Follow record);
}