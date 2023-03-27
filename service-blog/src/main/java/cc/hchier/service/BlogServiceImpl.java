package cc.hchier.service;

import cc.hchier.consts.NoticeType;
import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.dto.NoticeAddDTO;
import cc.hchier.entity.Blog;
import cc.hchier.mapper.BlogFavorMapper;
import cc.hchier.mapper.BlogMapper;
import cc.hchier.vo.BlogTinyVO;
import cc.hchier.vo.BlogVO;
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
 * @Date 2023/2/20 18:52
 */
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;
    private final RabbitTemplate rabbitTemplate;
    private final TopicService topicService;
    private final BlogFavorMapper blogFavorMapper;
    private final UserService userService;

    public BlogServiceImpl(BlogMapper blogMapper, RabbitTemplate rabbitTemplate, TopicService topicService, BlogFavorMapper blogFavorMapper, UserService userService) {
        this.blogMapper = blogMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.topicService = topicService;
        this.blogFavorMapper = blogFavorMapper;
        this.userService = userService;
    }

    @Override
    public RestResponse<Object> delete(int id, String publisher) {
        if (blogMapper.deleteByPrimaryKey(id, publisher) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @GlobalTransactional
    @Override
    public RestResponse<Integer> publish(BlogPublishDTO dto) throws TransactionException {
        String xid = RootContext.getXID();
        int incrDiscussionNumCode = ResponseCode.OK.getCode();
        if (dto.getTopic() != null && !dto.getTopic().isEmpty()) {
            incrDiscussionNumCode = topicService.incrDiscussionNum(dto.getTopic(), 1).getCode();
        }
        TransactionManager manager = TransactionManagerHolder.get();
        if (blogMapper.insert(dto) == 0 ||
            incrDiscussionNumCode != ResponseCode.OK.getCode() ||
            !userService.incrBlogNum(dto.getPublisher(), 1).equals(RestResponse.ok())) {
            manager.rollback(xid);
            return RestResponse.fail();
        }
        manager.commit(xid);

        rabbitTemplate.convertAndSend(
            "directExchange",
            "sendNotice",
            new NoticeAddDTO()
                .setSender(dto.getPublisher())
                .setType(NoticeType.BLOG_PUBLISH_NOTICE.getCode())
                .setContent(dto.getTitle())
                .setLink(String.valueOf(dto.getId()))
                .setCreateTime(new Date()));

        if (dto.getTopic() != null) {
            rabbitTemplate.convertAndSend(
                "directExchange",
                "sendNotice",
                new NoticeAddDTO()
                    .setSender(dto.getPublisher())
                    //更新话题时，将话题名暂时放在这儿传过去
                    .setReceiver(dto.getTopic())
                    .setType(NoticeType.TOPIC_UPDATE_NOTICE.getCode())
                    .setContent(dto.getTitle())
                    .setLink(String.valueOf(dto.getId()))
                    .setCreateTime(new Date()));
        }

        return RestResponse.ok(dto.getId());
    }

    @Override
    public RestResponse<Object> get(int id, String currentUser) {
        Blog blog = blogMapper.selectByPrimaryKey(id);
        if (blog == null) {
            return RestResponse.build(ResponseCode.BLOG_NOT_EXIST);
        }
        //如果blog被隐藏或仅自我可见，且当前用户不为blog作者，则拒绝访问
        if ((blog.getSelfVisible() || blog.getHidden()) && !blog.getPublisher().equals(currentUser)) {
            return RestResponse.build(ResponseCode.PERMISSION_DENIED);
        }

        BlogVO blogVO = BlogService.map(blog)
            .setFavored(blogFavorMapper.favorCount(id, currentUser) > 0)
            .setUpdatePermission(blog.getPublisher().equals(currentUser));

        return RestResponse.ok(blogVO);
    }

    @Override
    public RestResponse<Object> update(BlogUpdateDTO dto) {
        if (blogMapper.update(dto) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<String> getAuthorById(int id) {
        return RestResponse.ok(blogMapper.getAuthorById(id));
    }

    @Override
    public RestResponse<List<BlogTinyVO>> getPublishedList(String publisher, int startIndex, int rowNum) {
        return RestResponse.ok(blogMapper.getTinyList(publisher, null, startIndex, rowNum));
    }

    @Override
    public RestResponse<List<BlogTinyVO>> getFavoredList(String liker, int startIndex, int rowNum) {
        return RestResponse.ok(blogMapper.getTinyList(null, liker, startIndex, rowNum));
    }

    @Override
    public RestResponse<List<BlogVO>> getListByTopic(String topic, int startIndex, int rowNum, String currentUser) {
        List<Blog> blogList = blogMapper.getListByTopic(topic, startIndex, rowNum);
        List<BlogVO> blogVOList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogVOList.add(BlogService.map(blog)
                .setFavored(blogFavorMapper.favorCount(blog.getId(), currentUser) > 0)
                .setUpdatePermission(blog.getPublisher().equals(currentUser)));
        }
        return RestResponse.ok(blogVOList);
    }

    @Override
    public RestResponse<List<BlogVO>> getHomeList(String currentUser) {
        List<Blog> blogList = blogMapper.getHomeList();
        List<BlogVO> blogVOList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogVOList.add(BlogService.map(blog)
                .setFavored(blogFavorMapper.favorCount(blog.getId(), currentUser) > 0)
                .setUpdatePermission(blog.getPublisher().equals(currentUser)));
        }
        return RestResponse.ok(blogVOList);
    }
}
