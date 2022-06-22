package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.Video;
import xyz.hchier.hzone.service.VideoService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:27
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Video record) {
        return 0;
    }

    @Override
    public Video selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Video> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Video record) {
        return 0;
    }
}
