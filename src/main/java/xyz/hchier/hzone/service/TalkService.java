package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Talk;

import java.util.List;

/**
 * @author Hchier
 */
public interface TalkService {
    RestResponse deleteByPrimaryKey(Integer id);

    RestResponse insert(Talk record);

    RestResponse<Talk> selectByPrimaryKey(Integer id);

    RestResponse<List<Talk>> selectAll();

    RestResponse updateByPrimaryKey(Talk record);
}