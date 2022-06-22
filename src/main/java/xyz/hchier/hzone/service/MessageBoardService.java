package xyz.hchier.hzone.service;

import xyz.hchier.hzone.entity.MessageBoard;

import java.util.List;

/**
 * @author Hchier
 */
public interface MessageBoardService {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageBoard record);

    MessageBoard selectByPrimaryKey(Integer id);

    List<MessageBoard> selectAll();

    int updateByPrimaryKey(MessageBoard record);
}