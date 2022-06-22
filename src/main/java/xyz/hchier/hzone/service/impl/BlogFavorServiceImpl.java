package xyz.hchier.hzone.service.impl;

import org.springframework.stereotype.Service;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.service.BlogFavorService;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/22 16:24
 */
@Service
public class BlogFavorServiceImpl implements BlogFavorService {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(BlogFavor record) {
        return 0;
    }

    @Override
    public BlogFavor selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<BlogFavor> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(BlogFavor record) {
        return 0;
    }
}
