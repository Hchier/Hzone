package xyz.hchier.hzone.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.hchier.hzone.base.BaseUtils;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Hchier
 * @Date 2022/6/23 15:26
 */
@WebFilter(urlPatterns = {"/api/*"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {
    private RedisTemplate redisTemplate;
    private ObjectMapper objectMapper;

    public LoginFilter(RedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (BaseUtils.getCurrentUser(httpServletRequest) == null) {
            httpServletResponse.getWriter().println(
                objectMapper.writeValueAsString(RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage()))
            );
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
