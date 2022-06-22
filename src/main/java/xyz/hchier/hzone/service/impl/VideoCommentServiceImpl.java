package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.VideoComment;
import xyz.hchier.hzone.service.VideoCommentService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:26
 */
@Service
public class VideoCommentServiceImpl implements VideoCommentService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(VideoComment record) {
        return 0;
    }

    @Override
    public VideoComment selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<VideoComment> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(VideoComment record) {
        return 0;
    }
}
