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
    void loadBlogFavorOfUser();
}
