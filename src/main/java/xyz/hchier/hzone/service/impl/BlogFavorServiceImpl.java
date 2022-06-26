package xyz.hchier.hzone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.*;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.mapper.BlogFavorMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.service.BlogFavorService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:23
 */
@Slf4j
@Service
public class BlogFavorServiceImpl implements BlogFavorService {
    private RedisTemplate redisTemplate;
    private BlogFavorMapper blogFavorMapper;
    private BlogMapper blogMapper;

    public BlogFavorServiceImpl(RedisTemplate redisTemplate, BlogFavorMapper blogFavorMapper, BlogMapper blogMapper) {
        this.redisTemplate = redisTemplate;
        this.blogFavorMapper = blogFavorMapper;
        this.blogMapper = blogMapper;
    }

    /**
     * 将点赞情况插入redis中
     *
     * @param blogFavor 博客有利
     * @param request   请求
     * @return 若已经点赞了，返回fail。将点赞信息存入【用户新增的点赞情况】和【用户博客点赞情况】，redis中存放的博客id对应的点赞量+1，
     * 事务成功执行返回执行情况的List，失败则log.error()并返回null。
     */
    @Override
    @SuppressWarnings("unchecked")
    public RestResponse favor(BlogFavor blogFavor, HttpServletRequest request) {
        blogFavor.setPraiser(BaseUtils.getCurrentUser(request));
        if (redisTemplate.opsForSet().isMember(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId())) {
            //讲真，在这里
            return RestResponse.fail(ResponseCode.BLOG_FAVOR_INFO_REPEAT.getCode(), ResponseCode.BLOG_FAVOR_INFO_REPEAT.getMessage());
        }
        blogFavor.setCreateTime(new Date());
        List<Object> execRes = (List<Object>) redisTemplate.execute(new SessionCallback() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + blogFavor.getPraiser(), blogFavor);
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    if (operations.opsForHash().get(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), String.valueOf(blogFavor.getBlogId())) == null) {
                        operations.opsForHash().put(
                            RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(),
                            String.valueOf(blogFavor.getBlogId()),
                            blogMapper.selectBlogFavorNumById(blogFavor.getBlogId()));
                    }
                    operations.opsForHash().increment(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), String.valueOf(blogFavor.getBlogId()), 1);
                    return operations.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                    operations.discard();
                    log.error(String.format("插入BlogFavor失败，BlogFavor: %s，e: %s", blogFavor.toString(), e.getMessage()));
                    return null;
                }
            }
        });
        return execRes != null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.BLOG_FAVOR_FAIL.getCode(), ResponseCode.BLOG_FAVOR_FAIL.getMessage());
    }

    @Override
    public RestResponse cancelFavor(BlogFavor blogFavor, HttpServletRequest request) {
        blogFavor.setPraiser(BaseUtils.getCurrentUser(request));
        if (!redisTemplate.opsForSet().isMember(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId())) {
            //讲真，在这里，redis中查不到应该去mysql中最后确认到底有没有，但是我不想做了。
            return RestResponse.fail(ResponseCode.BLOG_FAVOR_INFO_NOT_EXIST.getCode(), ResponseCode.BLOG_FAVOR_INFO_NOT_EXIST.getMessage());
        }
        List res = (List) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    operations.opsForSet().remove(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    return operations.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        return res != null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getCode(), ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getMessage());
    }
}
