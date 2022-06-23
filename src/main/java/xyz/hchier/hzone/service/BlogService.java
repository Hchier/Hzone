package xyz.hchier.hzone.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.dto.BlogDTO;
import xyz.hchier.hzone.entity.Blog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Hchier
 */
public interface BlogService {
    @TestPass
    RestResponse publish(BlogDTO blogDTO, HttpServletRequest request) throws JsonProcessingException;

    RestResponse update(BlogDTO blogDTO,HttpServletRequest request) throws JsonProcessingException;

    RestResponse getBlog(Integer id);
}