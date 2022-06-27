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
import xyz.hchier.hzone.service.RedisService;

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
    private RedisService redisService;

    public BlogFavorServiceImpl(RedisTemplate redisTemplate, BlogFavorMapper blogFavorMapper, BlogMapper blogMapper, RedisService redisService) {
        this.redisTemplate = redisTemplate;
        this.blogFavorMapper = blogFavorMapper;
        this.blogMapper = blogMapper;
        this.redisService = redisService;
    }

    /**
     * 将点赞情况插入redis中。保证博客存在
     *
     * @param blogFavor 博客有利
     * @param request   请求
     * @return 若已经点赞了，返回fail。将点赞信息存入【用户新增的点赞情况】和【用户博客点赞情况】，redis中存放的博客id对应的点赞量+1，
     * 事务成功执行返回执行情况的List，失败则log.error()并返回null。
     */
    @Override
    @SuppressWarnings("unchecked")
    public RestResponse favor(BlogFavor blogFavor, HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        blogFavor.setPraiser(currentUser);
        if (redisTemplate.opsForSet().isMember(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId())) {
            return RestResponse.fail(ResponseCode.BLOG_FAVOR_INFO_REPEAT.getCode(), ResponseCode.BLOG_FAVOR_INFO_REPEAT.getMessage());
        }
        blogFavor.setCreateTime(new Date());
        return redisService.updateBlogFavor(blogFavor, currentUser) != null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.BLOG_FAVOR_FAIL.getCode(), ResponseCode.BLOG_FAVOR_FAIL.getMessage());
    }

    @Override
    public RestResponse cancelFavor(BlogFavor blogFavor, HttpServletRequest request) {
        String currentUser = BaseUtils.getCurrentUser(request);
        blogFavor.setPraiser(currentUser);
        if (!redisTemplate.opsForSet().isMember(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId())) {
            return RestResponse.fail(ResponseCode.BLOG_FAVOR_INFO_NOT_EXIST.getCode(), ResponseCode.BLOG_FAVOR_INFO_NOT_EXIST.getMessage());
        }
        return redisService.updateBlogFavorCancel(blogFavor, currentUser) != null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getCode(), ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getMessage());
    }
}
