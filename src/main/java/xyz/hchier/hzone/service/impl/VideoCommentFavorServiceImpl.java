package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.VideoCommentFavor;
import xyz.hchier.hzone.service.VideoCommentFavorService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:26
 */
@Service
public class VideoCommentFavorServiceImpl implements VideoCommentFavorService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(VideoCommentFavor record) {
        return 0;
    }

    @Override
    public VideoCommentFavor selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<VideoCommentFavor> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(VideoCommentFavor record) {
        return 0;
    }
}
