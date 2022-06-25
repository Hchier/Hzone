package xyz.hchier.hzone.service;

import xyz.hchier.hzone.base.RestResponse;
import xyz.hchier.hzone.entity.BlogComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hchier
 */

public interface BlogCommentService {

    RestResponse publish(BlogComment record, HttpServletRequest request);


}