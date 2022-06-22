package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.MessageBoard;
import xyz.hchier.hzone.service.MessageBoardService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:25
 */
@Service
public class MessageBoardServiceImpl implements MessageBoardService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(MessageBoard record) {
        return 0;
    }

    @Override
    public MessageBoard selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<MessageBoard> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(MessageBoard record) {
        return 0;
    }
}
