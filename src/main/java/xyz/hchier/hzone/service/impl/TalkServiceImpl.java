package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.Talk;
import xyz.hchier.hzone.service.TalkService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:25
 */
@Service
public class TalkServiceImpl implements TalkService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Talk record) {
        return 0;
    }

    @Override
    public Talk selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Talk> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Talk record) {
        return 0;
    }
}
