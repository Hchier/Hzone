package xyz.hchier.hzone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.hchier.hzone.base.Const;
import xyz.hchier.hzone.base.RedisKeys;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.mapper.BlogFavorMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogService;
import xyz.hchier.hzone.service.RedisService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:13
 */
@Slf4j
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
        for (BlogFavor blogFavor : blogFavorList) {
            redisTemplate.opsForSet().add(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
        }
    }

    /**
     * 将用户点赞和取消点赞的信息更新至mysql中
     */
    @Override
    public void updateBlogFavorToMysql(String username) {
        Set<BlogFavor> addSet = redisTemplate.opsForSet().members(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + username);
        if (addSet != null && !addSet.isEmpty()) {
            int res1 = blogFavorMapper.multiInsert(addSet);
            redisTemplate.delete(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + username);
        }

        Set<Integer> cancelSet = redisTemplate.opsForSet().members(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + username);
        if (cancelSet != null && !cancelSet.isEmpty()) {
            int res2 = blogFavorMapper.multiDelete(cancelSet,username);
            redisTemplate.delete(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + username);
        }

    }

    /**
     * 删除过期的会话
     */
    @Override
    public void removeExpiredSessionIds() {
        Set<String> set = redisTemplate.opsForZSet().rangeByScore(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), 0, System.currentTimeMillis());
        if (set != null) {
            String username;
            for (String sessionId : set) {
                username = (String) redisTemplate.opsForHash().get(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId);
                redisTemplate.opsForHash().delete(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId);
                redisTemplate.opsForZSet().remove(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), sessionId);
                log.info(String.format("删除了过期的sessionId: %s, username: %s", sessionId, username));
            }

        }
    }

    /**
     * 将sessionId, username存入redis、用zset记录过期时间
     *
     * @param sessionId 会话id
     * @param username  用户名
     */
    @Override
    public void addSessionIdAndUsername(String sessionId, String username) {
        redisTemplate.opsForHash().put(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId, username);
        redisTemplate.opsForZSet().add(
            RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(),
            sessionId,
            System.currentTimeMillis() + Const.EXPIRE_TIME_OF_SESSION);
    }

    @Override
    public void loadBlogIdAndFavorNum(Integer blogId, Integer favorNum) {
        redisTemplate.opsForHash().put(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), blogId, favorNum);

    }

    @Override
    public void incrBlogFavorNum(Integer blogId, int increment) {
        redisTemplate.opsForHash().increment(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), blogId, increment);
    }


}
