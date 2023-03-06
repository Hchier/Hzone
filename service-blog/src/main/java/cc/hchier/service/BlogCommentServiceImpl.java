package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.consts.NoticeType;
import cc.hchier.dto.BlogCommentDeleteDTO;
import cc.hchier.dto.BlogCommentGetDTO;
import cc.hchier.dto.BlogCommentPublishDTO;
import cc.hchier.dto.NoticeAddDTO;
import cc.hchier.entity.BlogComment;
import cc.hchier.mapper.BlogCommentMapper;
import cc.hchier.mapper.BlogMapper;
import cc.hchier.vo.BlogCommentVO;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.TransactionManagerHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/22 14:27
 */
@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    private final BlogCommentMapper blogCommentMapper;
    private final BlogMapper blogMapper;
    private final RabbitTemplate rabbitTemplate;


    public BlogCommentServiceImpl(BlogCommentMapper blogCommentMapper, BlogMapper blogMapper, RabbitTemplate rabbitTemplate) {
        this.blogCommentMapper = blogCommentMapper;
        this.blogMapper = blogMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GlobalTransactional
    @Override
    public RestResponse<BlogCommentVO> publish(BlogCommentPublishDTO dto) throws TransactionException {
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (blogCommentMapper.insert(dto) == 0 || blogMapper.incrCommentNum(dto.getBlogId(), 1) == 0) {
            manager.rollback(xid);
            return RestResponse.fail();
        } else {
            if (dto.getBaseComment() != -1) {
                if (blogCommentMapper.incrCommentNum(dto.getBaseComment(), 1) == 0) {
                    manager.rollback(xid);
                    return RestResponse.fail();
                }
                if (!dto.getBaseComment().equals(dto.getCommentOf())) {
                    if (blogCommentMapper.incrCommentNum(dto.getCommentOf(), 1) == 0) {
                        manager.rollback(xid);
                        return RestResponse.fail();
                    }
                }
            }
        }
        manager.commit(xid);
        //博客的评论
        if (dto.getCommentOf() == -1) {
            rabbitTemplate.convertAndSend(
                "directExchange",
                "sendNotice",
                new NoticeAddDTO()
                    .setSender(dto.getPublisher())
                    .setReceiver(blogMapper.getAuthorById(dto.getBlogId()))
                    .setType(NoticeType.BLOG_REPLIED_NOTICE.getCode())
                    .setContent(dto.getContent())
                    .setLink(String.valueOf(dto.getId()))
                    .setCreateTime(new Date())
            );
        }
        //博客评论的评论
        else {
            rabbitTemplate.convertAndSend(
                "directExchange",
                "sendNotice",
                new NoticeAddDTO()
                    .setSender(dto.getPublisher())
                    .setReceiver(dto.getReceiver())
                    .setType(NoticeType.BLOG_COMMENT_REPLIED_NOTICE.getCode())
                    .setContent(dto.getContent())
                    .setLink(String.valueOf(dto.getId()))
                    .setCreateTime(new Date())
            );
        }

        return RestResponse.ok(new BlogCommentVO().
            setId(dto.getId()).
            setPublisher(dto.getPublisher())
            .setCreateTime(dto.getCreateTime()));
    }

    @GlobalTransactional
    @Override
    public RestResponse<Object> delete(BlogCommentDeleteDTO dto) throws TransactionException {
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (!blogCommentMapper.delete(dto.getId(), dto.getPublisher()) || blogMapper.incrCommentNum(dto.getBlogId(), -1) == 0) {
            manager.rollback(xid);
            return RestResponse.fail();
        } else {
            if (dto.getBaseComment() != -1) {
                if (blogCommentMapper.incrCommentNum(dto.getBaseComment(), -1) == 0) {
                    manager.rollback(xid);
                    return RestResponse.fail();
                }
                if (!dto.getBaseComment().equals(dto.getCommentOf())) {
                    if (blogCommentMapper.incrCommentNum(dto.getCommentOf(), -1) == 0) {
                        manager.rollback(xid);
                        return RestResponse.fail();
                    }
                }
            }
        }
        manager.commit(xid);
        return RestResponse.ok();
    }

    @Override
    public RestResponse<List<BlogCommentVO>> get(BlogCommentGetDTO dto) {
        List<BlogComment> blogCommentList = blogCommentMapper.selectByBlogId(dto.getBlogId(), dto.getBaseComment(), dto.getPageNum() * 20, 20);
        List<BlogCommentVO> blogCommentVOList = new ArrayList<>();
        for (BlogComment blogComment : blogCommentList) {
            blogCommentVOList.add(
                new BlogCommentVO(
                    blogComment.getId(),
                    blogComment.getPublisher(),
                    blogComment.getReceiver(),
                    blogComment.getBlogId(),
                    blogComment.getContent(),
                    blogComment.getCommentNum(),
                    blogComment.getFavorNum(),
                    blogComment.getHidden(),
                    blogComment.getBaseComment(),
                    blogComment.getCommentOf(),
                    dto.getCurrentUser(),
                    blogComment.getCreateTime(),
                    blogComment.getPublisher().equals(dto.getCurrentUser())
                )
            );
        }
        return RestResponse.ok(blogCommentVOList);
    }

    @Override
    public RestResponse<Object> hidden(int blogId, int commentId, String currentUser) {
        if (blogCommentMapper.hidden(blogId, commentId, currentUser) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }
}
