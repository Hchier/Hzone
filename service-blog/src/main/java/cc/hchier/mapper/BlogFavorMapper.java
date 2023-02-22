package cc.hchier.mapper;

import cc.hchier.entity.BlogFavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface BlogFavorMapper {
    /**
     * 删除点赞记录
     *
     * @param blogId 博客id
     * @param liker  点赞者
     * @return int
     */
    int delete(@Param("blogId") Integer blogId, @Param("liker") String liker);

    /**
     * 插入点赞记录
     *
     * @param record 记录
     * @return int
     */
    int insert(BlogFavor record);

    /**
     * 计数
     *
     * @param blogId 博客id
     * @param liker  点赞者
     * @return int
     */
    int favorCount(@Param("blogId") Integer blogId, @Param("liker") String liker);

    /**
     * 点赞列表
     *
     * @param liker      喜欢人
     * @param startIndex 开始指数
     * @param rowNum     行num
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> favorList(@Param("liker") String liker, @Param("startIndex") Integer startIndex, @Param("rowNum") Integer rowNum);

}