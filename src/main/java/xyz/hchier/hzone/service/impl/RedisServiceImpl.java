package xyz.hchier.hzone.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import xyz.hchier.hzone.base.Const;
import xyz.hchier.hzone.base.RedisKeys;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.entity.BlogFavor;
import xyz.hchier.hzone.mapper.BlogFavorMapper;
import xyz.hchier.hzone.mapper.BlogMapper;
import xyz.hchier.hzone.mapper.UserMapper;
import xyz.hchier.hzone.service.MqProducerService;
import xyz.hchier.hzone.service.RedisService;

import java.util.*;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:13
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    private RedisTemplate redisTemplate;
    private BlogFavorMapper blogFavorMapper;
    private BlogMapper blogMapper;
    private UserMapper userMapper;
    private MqProducerService mqProducerService;

    public RedisServiceImpl(RedisTemplate redisTemplate, BlogFavorMapper blogFavorMapper, BlogMapper blogMapper, UserMapper userMapper, MqProducerService mqProducerService) {
        this.redisTemplate = redisTemplate;
        this.blogFavorMapper = blogFavorMapper;
        this.blogMapper = blogMapper;
        this.userMapper = userMapper;
        this.mqProducerService = mqProducerService;
    }


    /**
     * 将用户的点赞过的blog的id加载到redis中
     */
    @Override
    public void loadBlogFavorByUsername(String username) {
        List<BlogFavor> blogFavorList = blogFavorMapper.selectFavorInfo(username);
        for (BlogFavor blogFavor : blogFavorList) {
            redisTemplate.opsForSet().add(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
        }
    }

    /**
     * 将新增的用户点赞和取消点赞的信息更新至mysql中，写完后将key删了
     */
    @Override
    public void writeBlogFavorOfUser(String username) {
        Set<BlogFavor> addSet = redisTemplate.opsForSet().members(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + username);
        if (addSet != null && !addSet.isEmpty()) {
            if (blogFavorMapper.multiInsert(addSet) > 0) {
                redisTemplate.delete(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + username);
                log.info(String.format("将用户%s新增的博客点赞信息%s插入mysql了", username, Arrays.toString(addSet.toArray())));
            }

        }

        Set<Integer> cancelSet = redisTemplate.opsForSet().members(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + username);
        if (cancelSet != null && !cancelSet.isEmpty()) {
            if (blogFavorMapper.multiDelete(cancelSet, username) > 0) {
                redisTemplate.delete(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + username);
                log.info(String.format("将用户%s新取消的博客点赞信息%s从mysql中移除了", username, Arrays.toString(addSet.toArray())));
            }

        }

    }

    /**
     * 将redis中的博客点赞数写入mysql
     *
     * @return int
     */
    @Override
    public int writeBlogFavorNum() {
        Set<String> keySet = redisTemplate.opsForHash().keys(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey());
        List<Blog> blogList = new LinkedList<>();
        for (String id : keySet) {
            int favorNum = (int) redisTemplate.opsForHash().get(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), id);
            if (favorNum != -1) {
                blogList.add(new Blog().setId(Integer.valueOf(id)).setFavorNum(favorNum));
                mqProducerService.sendMsgToDirectExchange(
                    "directExchange",
                    "sendEmail",
                    userMapper.selectEmailByBlogId(Integer.valueOf(id)));
            }
        }
        int res = 0;
        if (!blogList.isEmpty()) {
            res = blogMapper.multiUpdateBlogFavorNum(blogList);
        }
        return res;
    }

    /**
     * 检查 有没有过期了的session，有的话就删了
     */
    @Override
    public void checkAndProcessExpiredSessions() {
        Set<String> set = redisTemplate.opsForZSet().rangeByScore(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), 0, System.currentTimeMillis());
        if (set != null && !set.isEmpty()) {
            for (String sessionId : set) {
                this.processExpiredSession(sessionId);
            }
        }
    }

    /**
     * 处理过期的会话
     * 删除过期的session、将该过期session对应的用户新增的博客点赞与取消点赞的情况写入redis
     *
     * @param sessionId 会话id
     */
    @Override
    public void processExpiredSession(String sessionId) {
        String username = (String) redisTemplate.opsForHash().get(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId);
        redisTemplate.opsForHash().delete(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId);
        redisTemplate.opsForZSet().remove(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), sessionId);
        redisTemplate.delete(RedisKeys.BLOG_FAVOR_OF.getKey() + username);
        log.info(String.format("删除了过期的sessionId: %s, username: %s", sessionId, username));
        this.writeBlogFavorOfUser(username);
    }

    /**
     * 将sessionId, username存入redis、用zset记录过期时间
     *
     * @param sessionId 会话id
     * @param username  用户名
     */
    @Override
    public void addSessionIdAndUsername(String sessionId, String username) {
        //
        if (redisTemplate.opsForHash().hasKey(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId)) {
            this.processExpiredSession(sessionId);
        }
        redisTemplate.opsForHash().put(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId, username);
        redisTemplate.opsForZSet().add(
            RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(),
            sessionId,
            System.currentTimeMillis() + Const.EXPIRE_TIME_OF_SESSION);
    }


    @Override
    public String getUsernameBySessionId(String sessionId) {
        return (String) redisTemplate.opsForHash().get(RedisKeys.SESSION_ID_AND_USERNAME.getKey(), sessionId);
    }

    /**
     * 增加session有效时间
     *
     * @param sessionId 会话id
     * @param threshold 阈值
     * @param increment 增量
     * @return double
     * 当session的剩余有效时间小于threshold时，增加increment
     */
    @Override
    public double incrValidTimeOfSession(String sessionId, int threshold, int increment) {
        if ((redisTemplate.opsForZSet().score(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), sessionId)) - System.currentTimeMillis() < threshold) {
            return redisTemplate.opsForZSet().incrementScore(RedisKeys.SESSION_ID_AND_EXPIRE_TIME.getKey(), sessionId, increment);
        }
        return -1;
    }

    /**
     * 更新点赞数。保证redis中存在博客点赞数
     * 要更新的有 BLOG_FAVOR_TO_ADD_OF、BLOG_FAVOR_OF、BLOG_ID_AND_FAVOR_NUM
     *
     * @param blogFavor 博客有利
     * @param username  用户名
     * @return {@link List}<{@link Object}>
     */
    @Override
    public RestResponse updateBlogFavor(BlogFavor blogFavor, String username) {
        return (List<Object>) redisTemplate.execute(new SessionCallback() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_TO_ADD_OF.getKey() + blogFavor.getPraiser(), blogFavor);
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    operations.opsForHash().increment(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), String.valueOf(blogFavor.getBlogId()), 1);
                    return operations.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                    operations.discard();
                    log.error(String.format("插入BlogFavor失败，BlogFavor: %s，e: %s", blogFavor.toString(), e.getMessage()));
                    return null;
                }
            }
        }) != null ? RestResponse.ok() : RestResponse.fail(ResponseCode.BLOG_FAVOR_FAIL.getCode(), ResponseCode.BLOG_FAVOR_FAIL.getMessage());
    }

    @Override
    public RestResponse updateBlogFavorCancel(BlogFavor blogFavor, String username) {
        return (List<Object>) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    operations.opsForSet().add(RedisKeys.BLOG_FAVOR_TO_CANCEL_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    operations.opsForSet().remove(RedisKeys.BLOG_FAVOR_OF.getKey() + blogFavor.getPraiser(), blogFavor.getBlogId());
                    operations.opsForHash().increment(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), String.valueOf(blogFavor.getBlogId()), -1);
                    return operations.exec();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }) != null ?
            RestResponse.ok() :
            RestResponse.fail(ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getCode(), ResponseCode.BLOG_FAVOR_CANCEL_FAIL.getMessage());
    }

    /**
     * 根据博客id从mysql中加载博客点赞数至redis中，当博客不存在时，点赞数设为-1
     * 可能出现的问题，现在不存在的id，以后可能存在，所以当根据id查询博客时或发表博客时，要及时跟新点赞数
     *
     * @param id id
     * @return 点赞数
     * -1：mysql中不存在该博客，但是也将点赞数设为-1
     * 其它：博客存在且点赞数已被加载到redis中
     */
    @Override
    public Integer loadBlogFavorNumById(Integer id) {
        Integer favorNum = blogMapper.selectBlogFavorNumById(id);
        if (favorNum == null) {
            favorNum = -1;
        }
        updateBlogFavorNum(id, favorNum);
        return favorNum;
    }

    /**
     * 将redis中的博客点赞数更新为指定的值
     *
     * @param id       id
     * @param favorNum 支持全国矿工工会
     */
    @Override
    public void updateBlogFavorNum(Integer id, Integer favorNum) {
        redisTemplate.opsForHash().put(
            RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(),
            String.valueOf(id),
            favorNum);
    }

    /**
     * 根据博客id查询点赞数量
     * 先去redis中查，redis中有则返回，没有则调 loadBlogFavorNumById
     *
     * @param id id
     * @return -1：博客不存在
     */
    @Override
    public int getBlogFavorNumById(Integer id) {
        Integer favorNum = (Integer) redisTemplate.opsForHash().get(RedisKeys.BLOG_ID_AND_FAVOR_NUM.getKey(), String.valueOf(id));
        if (favorNum == null) {
            return this.loadBlogFavorNumById(id);
        }
        return favorNum;
    }
}
