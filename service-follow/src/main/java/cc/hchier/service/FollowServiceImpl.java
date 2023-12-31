package cc.hchier.service;

import cc.hchier.enums.ResponseCode;
import cc.hchier.response.RestResponse;
import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.mapper.FollowMapper;
import cc.hchier.vo.FollowTopicVO;
import cc.hchier.vo.FollowUserVO;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.core.model.TransactionManager;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.TransactionManagerHolder;
import org.springframework.stereotype.Service;

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
    public RestResponse<List<FollowTopicVO>> getFollowTopicList(String follower, String currentUser, Integer startIndex, Integer rowNum) {
        return RestResponse.ok(followMapper.selectTopicList(follower, currentUser, startIndex, rowNum));
    }

    @Override
    public RestResponse<List<FollowUserVO>> getFollowUserList(String follower, String currentUser, Integer startIndex, Integer rowNum) {
        return RestResponse.ok(followMapper.selectUserList(follower, currentUser, startIndex, rowNum));
    }

    @Override
    public RestResponse<List<FollowUserVO>> getFollowerList(String followee, Integer type, String currentUser, Integer startIndex, Integer rowNum) {
        return RestResponse.ok(followMapper.selectFollowerList(followee, type, currentUser, startIndex, rowNum));
    }
}
