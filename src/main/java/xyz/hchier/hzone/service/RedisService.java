package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.entity.BlogFavor;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:12
 */
public interface RedisService {
    @TestPass
    void loadBlogIdAndUsername();

    @TestPass
    void loadBlogFavorByUsername(String username);

    void writeBlogFavorOfUser(String username);

    int writeBlogFavorNum();

    void removeExpiredSessionIds();

    void addSessionIdAndUsername(String sessionId, String username);

    String getUsernameBySessionId(String sessionId);

    double incrValidTimeOfSession(String sessionId, int threshold, int increment);

    RestResponse updateBlogFavor(BlogFavor blogFavor, String username);

    RestResponse updateBlogFavorCancel(BlogFavor blogFavor, String username);

    Integer loadBlogFavorNumById(Integer id);

    void updateBlogFavorNum(Integer id, Integer favorNum);

    int getBlogFavorNumById(Integer id);
}
