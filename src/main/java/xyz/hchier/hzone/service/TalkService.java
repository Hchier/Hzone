package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.Talk;

import java.util.List;

/**
 * @author Hchier
 */
public interface TalkService {
    int deleteByPrimaryKey(Integer id);

    int insert(Talk record);

    Talk selectByPrimaryKey(Integer id);

    List<Talk> selectAll();

    int updateByPrimaryKey(Talk record);
}