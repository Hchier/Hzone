package xyz.hchier.hzone.mapper;

import java.util.List;
import xyz.hchier.hzone.entity.MessageBoard;

public interface MessageBoardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MessageBoard record);

    MessageBoard selectByPrimaryKey(Integer id);

    List<MessageBoard> selectAll();

    int updateByPrimaryKey(MessageBoard record);
}