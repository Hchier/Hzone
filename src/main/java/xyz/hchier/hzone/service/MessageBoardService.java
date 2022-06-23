package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.MessageBoard;

import java.util.List;

/**
 * @author Hchier
 */
public interface MessageBoardService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(MessageBoard record);

    RestResponse<MessageBoard> selectByPrimaryKey(Integer id);

    RestResponse<List<MessageBoard>> selectAll();

    RestResponse updateByPrimaryKey(MessageBoard record);
}