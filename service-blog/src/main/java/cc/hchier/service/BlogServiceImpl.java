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
import cc.hchier.vo.BlogVO;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.TransactionManagerHolder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    /**
     * 将{@link Blog}转为{@link BlogVO}。
     * 但是{@link BlogVO}中的favored和updatePermission在这儿搞不定
     *
     * @param blog 博客
     * @return {@link BlogVO}
     */
    BlogVO mapBlogToBlogVO(Blog blog, String currentUser) {
        return new BlogVO()
            .setId(blog.getId())
            .setPublisher(blog.getPublisher())
            .setTitle(blog.getTitle())
            .setContent(blog.getContent())
            .setFavorNum(blog.getFavorNum())
            .setCommentNum(blog.getCommentNum())
            .setRewardNum(blog.getRewardNum())
            .setSelfVisible(blog.getSelfVisible())
            .setFavored(blogFavorMapper.favorCount(blog.getId(), currentUser) > 0)
            .setHidden(blog.getHidden())
            .setCommentForbidden(blog.getCommentForbidden())
            .setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(blog.getUpdateTime()))
            .setUpdatePermission(blog.getPublisher().equals(currentUser))
            .setTopic(blog.getTopic());
    }

    List<BlogVO> mapBlogListToBlogVOList(List<Blog> blogList, String currentUser) {
        List<BlogVO> blogVOList = new ArrayList<>();
        for (Blog blog : blogList) {
            blogVOList.add(mapBlogToBlogVO(blog, currentUser));
        }
        return blogVOList;
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
            //todo 话题可能不存在，得新建话题
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

        return RestResponse.ok(mapBlogToBlogVO(blog, currentUser));
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
    public RestResponse<List<BlogVO>> getPublishedList(String publisher, String currentUser, int startIndex, int rowNum) {
        List<Blog> blogList = blogMapper.getPublishedOrFavorList(publisher, null, startIndex, rowNum);
        return RestResponse.ok(mapBlogListToBlogVOList(blogList, currentUser));
    }

    @Override
    public RestResponse<List<BlogVO>> getFavoredList(String liker, int startIndex, int rowNum) {
        List<Blog> blogList = blogMapper.getPublishedOrFavorList(null, liker, startIndex, rowNum);
        return RestResponse.ok(mapBlogListToBlogVOList(blogList, liker));
    }

    @Override
    public RestResponse<List<BlogVO>> getListByTopic(String topic, int startIndex, int rowNum, String currentUser) {
        List<Blog> blogList = blogMapper.getListByTopic(topic, startIndex, rowNum);
        return RestResponse.ok(mapBlogListToBlogVOList(blogList, currentUser));
    }

    @Override
    public RestResponse<List<BlogVO>> getHomeList(String currentUser) {
        List<Blog> blogList = blogMapper.getHomeList();
        return RestResponse.ok(mapBlogListToBlogVOList(blogList, currentUser));
    }
}
