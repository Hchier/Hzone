package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.TestPass;

/**
 * @author by Hchier
 * @Date 2022/6/25 10:12
 */
public interface RedisService {
    @TestPass
    void loadBlogIdAndUsername();

    @TestPass
    void loadBlogFavorOfUser(String username);

    void updateBlogFavorToMysql(String username);

    void removeExpiredSessionIds();

    void addSessionIdAndUsername(String sessionId, String username);

    void loadBlogIdAndFavorNum(Integer blogId, Integer favorNum);

    void incrBlogFavorNum(Integer blogId, int incremen);
}
