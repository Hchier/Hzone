package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.consts.NoticeType;
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
    public RestResponse<Integer> publish(BlogCommentPublishDTO dto) throws TransactionException {
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (blogCommentMapper.insert(dto) == 0 || blogMapper.incrCommentNum(dto.getBlogId(), 1) == 0) {
            manager.rollback(xid);
            return RestResponse.fail();
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
                    .setReceiver(blogCommentMapper.selectPublisherById(dto.getCommentOf()))
                    .setType(NoticeType.BLOG_COMMENT_REPLIED_NOTICE.getCode())
                    .setContent(dto.getContent())
                    .setLink(String.valueOf(dto.getId()))
                    .setCreateTime(new Date())
            );
        }

        return RestResponse.ok(dto.getId());
    }

    @GlobalTransactional
    @Override
    public RestResponse<Object> delete(int commentId, int blogId, String publisher) throws TransactionException {
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (blogCommentMapper.delete(commentId, publisher) == 0 || blogMapper.incrCommentNum(blogId, -1) == 0) {
            manager.rollback(xid);
            return RestResponse.fail();
        }
        manager.commit(xid);
        return RestResponse.ok();
    }

    @Override
    public RestResponse<List<BlogCommentVO>> get(int blogId, int commentOf, int pageNum, int rowNum, String currentUser) {
        List<BlogComment> blogCommentList = blogCommentMapper.selectByBlogId(blogId, commentOf, pageNum * rowNum, rowNum);
        List<BlogCommentVO> blogCommentVOList = new ArrayList<>();
        for (BlogComment blogComment : blogCommentList) {
            blogCommentVOList.add(
                new BlogCommentVO(
                    blogComment.getId(),
                    blogComment.getPublisher(),
                    blogComment.getBlogId(),
                    blogComment.getContent(),
                    blogComment.getCommentNum(),
                    blogComment.getFavorNum(),
                    currentUser,
                    blogComment.getCreateTime(),
                    blogComment.getPublisher().equals(currentUser)
                )
            );
        }
        return RestResponse.ok(blogCommentVOList);
    }

    @Override
    public RestResponse<Object> hidden(int commentId, int blogId, String currentUser) {
        if (blogCommentMapper.hidden(commentId, blogId, currentUser) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }
}