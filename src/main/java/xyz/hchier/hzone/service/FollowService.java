package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Follow;

import java.util.List;

/**
 * @author Hchier
 */
public interface FollowService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(Follow record);

    RestResponse<Follow> selectByPrimaryKey(Integer id);

    RestResponse<List<Follow>> selectAll();

    RestResponse updateByPrimaryKey(Follow record);
}