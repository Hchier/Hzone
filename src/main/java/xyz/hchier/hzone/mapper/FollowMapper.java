package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.Follow;

public interface FollowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Follow record);

    Follow selectByPrimaryKey(Integer id);

    List<Follow> selectAll();

    int updateByPrimaryKey(Follow record);
}