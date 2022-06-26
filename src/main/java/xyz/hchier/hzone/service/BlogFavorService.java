package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.entity.BlogFavor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hchier
 */
public interface BlogFavorService {
    /**
     * 将点赞情况插入redis中
     *
     * @return {@link RestResponse}
     */
    @TestPass
    RestResponse favor(BlogFavor blogFavor, HttpServletRequest request);

    /**
     * 取消点赞
     *
     * @param blogFavor
     * @param request   请求
     * @return {@link RestResponse}
     */
    @TestPass
    RestResponse cancelFavor(BlogFavor blogFavor, HttpServletRequest request);
}