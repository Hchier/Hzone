package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.Talk;

public interface TalkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Talk record);

    Talk selectByPrimaryKey(Integer id);

    List<Talk> selectAll();

    int updateByPrimaryKey(Talk record);
}