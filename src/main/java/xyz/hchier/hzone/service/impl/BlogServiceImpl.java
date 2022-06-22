package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.service.BlogService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:24
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Blog record) {
        return 0;
    }

    @Override
    public Blog selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Blog> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Blog record) {
        return 0;
    }
}
