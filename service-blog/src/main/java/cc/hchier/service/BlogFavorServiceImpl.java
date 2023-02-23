package cc.hchier.service;

import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.entity.BlogFavor;
import cc.hchier.mapper.BlogFavorMapper;
import cc.hchier.mapper.BlogMapper;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.GlobalStatus;
import io.seata.core.model.TransactionManager;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.TransactionManagerHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author by Hchier
 * @Date 2023/2/21 21:57
 */
@Service
public class BlogFavorServiceImpl implements BlogFavorService {
    private final BlogFavorMapper blogFavorMapper;
    private final BlogMapper blogMapper;
    private final UserService userService;

    public BlogFavorServiceImpl(BlogFavorMapper blogFavorMapper, BlogMapper blogMapper, UserService userService) {
        this.blogFavorMapper = blogFavorMapper;
        this.blogMapper = blogMapper;
        this.userService = userService;
    }


    @GlobalTransactional
    @Override
    public RestResponse<Object> favor(int blogId, String liker, String author) throws TransactionException {
        if (existFavor(blogId, liker)) {
            return RestResponse.build(ResponseCode.BLOG_FAVOR_REPEAT);
        }

        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (userService.incrFavoredNum(author, 1).getCode() != ResponseCode.OK.getCode() ||
            userService.incrFavorNum(liker, 1).getCode() != ResponseCode.OK.getCode() ||
            blogMapper.incrFavorNum(blogId, 1) == 0 ||
            blogFavorMapper.insert(new BlogFavor().setLiker(liker).setBlogId(blogId).setCreateTime(new Date())) == 0) {
            if (manager.rollback(xid).getCode() == GlobalStatus.RollbackFailed.getCode()) {
                //todo
            }
            return RestResponse.fail();
        }
        if (manager.commit(xid).getCode() == GlobalStatus.CommitFailed.getCode()) {
            //todo
            if (manager.rollback(xid).getCode() == GlobalStatus.RollbackFailed.getCode()) {
                //todo
            }
            return RestResponse.fail();
        }
        return RestResponse.ok();

    }

    @GlobalTransactional
    @Override
    public RestResponse<Object> unFavor(int blogId, String currentUser, String author) throws TransactionException {
        if (!existFavor(blogId, currentUser)) {
            return RestResponse.build(ResponseCode.BLOG_FAVOR_NOT_EXIST);
        }
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (userService.incrFavoredNum(author, -1).getCode() != ResponseCode.OK.getCode() ||
            userService.incrFavorNum(currentUser, -1).getCode() != ResponseCode.OK.getCode() ||
            blogMapper.incrFavorNum(blogId, -1) == 0 ||
            blogFavorMapper.delete(blogId, currentUser) == 0) {
            manager.rollback(xid);
            return RestResponse.fail();
        }
        if (manager.commit(xid).getCode() == GlobalStatus.CommitFailed.getCode()) {
            if (manager.rollback(xid).getCode() == GlobalStatus.RollbackFailed.getCode()) {
                //todo
            }
        }
        return RestResponse.ok();

    }

    @Override
    public boolean existFavor(int blogId, String liker) {
        return blogFavorMapper.favorCount(blogId, liker) > 0;
    }
}
