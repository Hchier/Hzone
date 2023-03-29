package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.vo.FollowTopicVO;
import cc.hchier.vo.FollowUserVO;

import io.seata.core.exception.TransactionException;
import java.util.List;

/**
 * @author hchier
 */
public interface FollowService {
    /**
     * 关注
     * 3件事：
     * 1，follow表插入关注记录。
     * 2，user表follower更新关注数。
     * 3，user表或topic表 followee更新被关注数。
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     * @throws TransactionException 交易异常
     */
    RestResponse<Object> follow(FollowDTO dto) throws TransactionException;


    /**
     * 取关
     * 3件事：
     * 1，follow表插入关注记录。
     * 2，user表follower更新关注数。
     * 3，user表或topic表 更新被关注数。
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     * @throws TransactionException 交易异常
     */
    RestResponse<Object> unFollow(FollowCancelDTO dto) throws TransactionException;

    /**
     * 是否存在关注
     *
     * @param follower 追随者
     * @param followee followee
     * @param type     类型
     * @return boolean
     */
    boolean existFollow(String follower, String followee, Integer type);

    /**
     * 查找关注某人或某话题的用户的用户名
     *
     * @param followee   followee
     * @param startIndex 开始index
     * @param rowNum     行num
     * @param type       类型
     * @return {@link RestResponse}<{@link List}<{@link String}>>
     */
    RestResponse<List<String>> followedList(String followee, Integer type, Integer startIndex, Integer rowNum);

    /**
     * 查找某人关注的主题
     *
     * @param follower    追随者
     * @param currentUser 当前用户
     * @param startIndex  开始指数
     * @param rowNum      行num
     * @return {@link RestResponse}<{@link List}<{@link FollowTopicVO}>>
     */
    RestResponse<List<FollowTopicVO>> getFollowTopicList(String follower, String currentUser, Integer startIndex, Integer rowNum);

    /**
     * 查找某人关注的用户
     *
     * @param follower    追随者
     * @param currentUser 当前用户
     * @param startIndex  开始指数
     * @param rowNum      行num
     * @return {@link RestResponse}<{@link List}<{@link FollowUserVO}>>
     */
    RestResponse<List<FollowUserVO>> getFollowUserList(String follower, String currentUser, Integer startIndex, Integer rowNum);
}
