package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.BlogCommentFavor;
import xyz.hchier.hzone.service.BlogCommentFavorService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:23
 */
@Service
public class BlogCommentFavorServiceImpl implements BlogCommentFavorService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(BlogCommentFavor record) {
        return 0;
    }

    @Override
    public BlogCommentFavor selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<BlogCommentFavor> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(BlogCommentFavor record) {
        return 0;
    }
}
