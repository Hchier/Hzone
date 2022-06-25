package xyz.hchier.hzone.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.dto.BlogDTO;
import xyz.hchier.hzone.entity.Blog;
import xyz.hchier.hzone.vo.BlogVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Hchier
 */
public interface BlogService {
    @TestPass
    RestResponse publish(BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException;

    @TestPass
    RestResponse update(BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException;

    @TestPass
    RestResponse<BlogVO> get(Integer id, HttpServletRequest request) throws JsonProcessingException;

    @TestPass
    RestResponse delete(Integer id, HttpServletRequest request);

    @TestPass
    List<Blog> selectAllIdAndPublisher();

    boolean blogExist(Integer id);
}