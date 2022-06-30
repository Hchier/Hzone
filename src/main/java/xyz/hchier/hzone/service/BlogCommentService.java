package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.base.TestPass;
import xyz.hchier.hzone.entity.BlogComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hchier
 */

public interface BlogCommentService {

    @TestPass
    RestResponse publish(BlogComment record, HttpServletRequest request);

    @TestPass
    RestResponse delete(Integer id, HttpServletRequest request) throws InterruptedException;

    RestResponse getLimit(int blogId, int pageNum, int pageSize);
}