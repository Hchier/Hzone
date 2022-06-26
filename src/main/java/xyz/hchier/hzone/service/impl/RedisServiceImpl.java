package xyz.hchier.hzone.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.RedisKeys;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.mapper.BlogFavorMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogService;
import xyz.hchier.hzone.service.RedisService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:13
 */
@Service
public class RedisServiceImpl implements RedisService {
    private BlogService blogService;
    private RedisTemplate redisTemplate;
    private BlogFavorMapper blogFavorMapper;
    private BlogMapper blogMapper;

    public RedisServiceImpl(BlogService blogService, RedisTemplate redisTemplate, BlogFavorMapper blogFavorMapper, BlogMapper blogMapper) {
        this.blogService = blogService;
        this.redisTemplate = redisTemplate;
        this.blogFavorMapper = blogFavorMapper;
        this.blogMapper = blogMapper;
    }

    /**
     * 将博客id和用户名加载到redis中
     */
    @Override
    public void loadBlogIdAndUsername() {
        List<Blog> blogList = blogService.selectAllIdAndPublisher();
        Iterator<Blog> it = blogList.iterator();
        while (it.hasNext()) {
            Blog blog = it.next();
            redisTemplate.opsForHash().put(RedisKeys.BLOG_ID_AND_USERNAME.getKey(), String.valueOf(blog.getId()), blog.getPublisher());
        }
    }

    /**
     * 将用户的点赞过的blog的id加载到redis中
     */
    @Override
    public void loadBlogFavorOfUser(String username) {
        List<BlogFavor> blogFavorList = blogFavorMapper.selectFavorInfo(username);
        Iterator<BlogFavor> it = blogFavorList.iterator();
        while (it.hasNext()) {
            BlogFavor blogFavor = it.next();
            redisTemplate.opsForSet().add(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
        }
    }

    /**
     * 将用户点赞和取消点赞的信息更新至mysql中
     */
    @Override
    public void updateBlogFavorToMysql() {
        Set set = redisTemplate.opsForZSet().rangeByScore(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), 0, System.currentTimeMillis());
    }


}
