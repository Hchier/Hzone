package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.consts.NoticeType;
import cc.hchier.dto.NoticeAddDTO;
import cc.hchier.entity.Notice;
import cc.hchier.mapper.NoticeMapper;
import cc.hchier.vo.FollowVO;
import cc.hchier.vo.NoticeVO;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/23 12:24
 */

@RabbitListener(queues = {"noticeQueue"})
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;
    private final FollowService followService;
    private final BlogService blogService;

    public NoticeServiceImpl(NoticeMapper noticeMapper, FollowService followService, BlogService blogService) {
        this.noticeMapper = noticeMapper;
        this.followService = followService;
        this.blogService = blogService;
    }

    @RabbitHandler
    private void sendNotice(NoticeAddDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        switch (dto.getType()) {
            case 1:
                if (!sendBlogPublishNotice(dto)) {
                    channel.basicNack(tag, false, false);
                }
                break;
            case 2:
                //todo
                break;
            case 3:
                if (!sendBlogRepliedNotice(dto)) {
                    channel.basicNack(tag, false, false);
                }
                break;
            case 4:
                //todo
                break;
            case 5:
                //todo
                break;
            default:
                //todo
                break;
        }
        channel.basicAck(tag, false);
    }

    /**
     * 用户发表博客时，通知所有关注了该用户的用户
     *
     * @param dto dto
     * @return boolean
     */
    private boolean sendBlogPublishNotice(NoticeAddDTO dto) {
        int pageNum = 0;
        while (true) {
            RestResponse<List<FollowVO>> followerList = followService.followedInfo(dto.getSender(), pageNum++);
            if (followerList.getBody().isEmpty()) {
                return true;
            }
            List<Notice> noticeList = new ArrayList<>();
            for (FollowVO follower : followerList.getBody()) {
                noticeList.add(new Notice()
                    .setSender(dto.getSender())
                    .setReceiver(follower.getFollower())
                    .setType(NoticeType.BLOG_PUBLISH_NOTICE.getCode())
                    .setContent(dto.getContent())
                    .setLink(dto.getLink())
                    .setCreateTime(dto.getCreateTime()));
            }
            if (addList(noticeList) == 0) {
                return false;
            }
        }
    }

    /**
     * 博客被回复后，通知博客作者
     *
     * @param dto dto
     * @return boolean
     */
    private boolean sendBlogRepliedNotice(NoticeAddDTO dto) {
        String author = blogService.getAuthorById(Integer.valueOf(dto.getLink())).getBody();
        return noticeMapper.insert(
            new Notice()
                .setSender(dto.getSender())
                .setReceiver(author)
                .setType(NoticeType.BLOG_REPLIED_NOTICE.getCode())
                .setContent(dto.getContent())
                .setLink(dto.getLink())
                .setCreateTime(dto.getCreateTime())) == 1;
    }

    @Override
    public int addList(List<Notice> noticeList) {
        return noticeMapper.insertList(noticeList);
    }

    @Override
    public RestResponse<Object> delete(int id, String receiver) {
        if (noticeMapper.delete(id, receiver) == 0) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }

    @Override
    public RestResponse<List<NoticeVO>> get(String receiver, int startIndex, int rowNum) {
        return RestResponse.ok(noticeMapper.selectByReceiver(receiver, startIndex, rowNum));
    }
}
