package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.VideoFavor;
import xyz.hchier.hzone.service.VideoFavorService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:26
 */
@Service
public class VideoFavorServiceImpl implements VideoFavorService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(VideoFavor record) {
        return 0;
    }

    @Override
    public VideoFavor selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<VideoFavor> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(VideoFavor record) {
        return 0;
    }
}
