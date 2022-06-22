package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.BlogComment;
import xyz.hchier.hzone.service.BlogCommentService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:24
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(BlogComment record) {
        return 0;
    }

    @Override
    public BlogComment selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<BlogComment> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(BlogComment record) {
        return 0;
    }
}
