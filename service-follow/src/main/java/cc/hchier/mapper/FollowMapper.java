package cc.hchier.mapper;

import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface FollowMapper {

    /**
     * 删除关注记录
     *
     * @param dto dto
     * @return int
     */
    int delete(FollowCancelDTO dto);

    /**
     * 插入关注记录
     *
     * @param follow 遵循
     * @return int
     */
    int insert(FollowDTO follow);


    /**
     * 计数
     *
     * @param follower 追随者
     * @param followee followee
     * @param type     类型
     * @return int
     */
    int getCount(@Param("follower") String follower, @Param("followee") String followee, @Param("type") Integer type);

    /**
     * 查找某人的关注情况
     *
     * @param follower   追随者
     * @param type   类型
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link List}<{@link Follow}>
     */
    List<Follow> selectByFollower(
        @Param("follower") String follower,
        @Param("type") Integer type,
        @Param("startIndex") Integer startIndex,
        @Param("rowNum") Integer rowNum);

    /**
     * 查找某人的被关注情况
     *
     * @param followee   followee
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link List}<{@link Follow}>
     */
    List<Follow> selectByFollowee(
        @Param("followee") String followee,
        @Param("startIndex") Integer startIndex,
        @Param("rowNum") Integer rowNum);

    /**
     * 查找关注某人或某话题的用户的用户名
     *
     * @param followee   followee
     * @param type       关注类型
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link List}<{@link String}>
     */
    List<String> selectFollowerUsernameByFollowee(
        @Param("followee") String followee,
        @Param("type") Integer type,
        @Param("startIndex") Integer startIndex,
        @Param("rowNum") Integer rowNum);
}