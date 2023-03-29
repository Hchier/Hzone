package cc.hchier.service;

import cc.hchier.consts.FollowType;
import cc.hchier.consts.ResponseCode;
import cc.hchier.RestResponse;
import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.entity.Follow;
import cc.hchier.mapper.FollowMapper;
import cc.hchier.vo.FollowVO;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.TransactionManagerHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/22 20:14
 */
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowMapper followMapper;
    private final UserService userService;
    private final TopicService topicService;

    public FollowServiceImpl(FollowMapper followMapper, UserService userService, TopicService topicService) {
        this.followMapper = followMapper;
        this.userService = userService;
        this.topicService = topicService;
    }


    @GlobalTransactional
    @Override
    public RestResponse<Object> follow(FollowDTO dto) throws TransactionException {
        if (existFollow(dto.getFollower(), dto.getFollowee(), dto.getType())) {
            return RestResponse.build(ResponseCode.FOLLOW_REPEAT);
        }
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (followMapper.insert(dto) == 0 ||
            userService.incrFollowNum(dto.getFollower(), 1).getCode() != ResponseCode.OK.getCode() ||
            ((dto.getType() == 1) ?
                userService.incrFollowedNum(dto.getFollowee(), 1) :
                topicService.incrFollowedNum(dto.getFollowee(), 1)).getCode() != ResponseCode.OK.getCode()
        ) {
            manager.rollback(xid);
            return RestResponse.fail();
        }
        manager.commit(xid);
        return RestResponse.ok();
    }

    @GlobalTransactional
    @Override
    public RestResponse<Object> unFollow(FollowCancelDTO dto) throws TransactionException {
        if (!existFollow(dto.getFollower(), dto.getFollowee(), dto.getType())) {
            return RestResponse.build(ResponseCode.FOLLOW_NOT_EXIST);
        }
        String xid = RootContext.getXID();
        TransactionManager manager = TransactionManagerHolder.get();

        if (followMapper.delete(dto) == 0 ||
            userService.incrFollowNum(dto.getFollower(), -1).getCode() != ResponseCode.OK.getCode() ||
            ((dto.getType() == 1) ? userService.incrFollowedNum(dto.getFollowee(), -1) : topicService.incrFollowedNum(dto.getFollowee(), -1)).getCode() != ResponseCode.OK.getCode()) {
            manager.rollback(xid);
            return RestResponse.fail();
        }
        manager.commit(xid);
        return RestResponse.ok();
    }

    @Override
    public boolean existFollow(String follower, String followee, Integer type) {
        return followMapper.getCount(follower, followee, type) > 0;
    }

    @Override
    public RestResponse<List<FollowVO>> followList(String follower, Integer type, String currentUser, Integer startIndex, Integer rowNum) {
        List<Follow> followList = followMapper.selectByFollower(follower, type, startIndex, rowNum);
        List<FollowVO> followVOList = new ArrayList<>();
        for (Follow follow : followList) {
            followVOList.add(new FollowVO(
                follow.getId(),
                follow.getType(),
                follow.getFollower(),
                follow.getFollowee(),
                existFollow(currentUser, follow.getFollowee(), FollowType.TOPIC.getCode())));
        }
        return RestResponse.ok(followVOList);
    }

    @Override
    public RestResponse<List<String>> followedList(String followee, Integer type, Integer startIndex, Integer rowNum) {
        List<String> followList = followMapper.selectFollowerUsernameByFollowee(followee, type, startIndex, rowNum);
        return RestResponse.ok(followList);
    }
}
