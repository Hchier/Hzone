package cc.hchier.mapper;

import cc.hchier.dto.BlogCommentPublishDTO;
import cc.hchier.entity.BlogComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface BlogCommentMapper {

    /**
     * 插入评论
     *
     * @param dto dto
     * @return int
     */
    int insert(BlogCommentPublishDTO dto);

    /**
     * 删除评论
     *
     * @param id        id
     * @param publisher 发布者
     * @return int
     */
    boolean delete(@Param("id") Integer id, @Param("publisher") String publisher);

    /**
     * 查找博客评论
     *
     * @param blogId      博客id
     * @param baseComment baseComment
     * @param startIndex  开始index
     * @param rowNum      行num
     * @param hidden      hidden
     * @return {@link List}<{@link BlogComment}>
     */
    List<BlogComment> selectByBlogId(@Param("blogId") Integer blogId,
                                     @Param("baseComment") Integer baseComment,
                                     @Param("startIndex") Integer startIndex,
                                     @Param("rowNum") Integer rowNum,
                                     @Param("hidden")Integer hidden);

    /**
     * 隐藏博客评论
     * 只能隐藏发表在自己博客下的评论
     *
     * @param id          评论id
     * @param currentUser 当前用户
     * @return int
     */
    int hidden(@Param("id") Integer id, @Param("currentUser") String currentUser);

    /**
     * 根据博客id查找评论者username
     *
     * @param id id
     * @return {@link String}
     */
    String selectPublisherById(@Param("id") Integer id);

    /**
     * 评论数+1
     *
     * @param id id
     * @param incr incr
     * @return int
     */
    int incrCommentNum(@Param("id") Integer id, @Param("incr") Integer incr);
}