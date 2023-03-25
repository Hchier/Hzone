package cc.hchier.mapper;

import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.entity.Blog;
import cc.hchier.vo.BlogTinyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author hchier
 */
@Mapper
public interface BlogMapper {
    /**
     * 删除博客
     *
     * @param id        id
     * @param publisher 作者
     * @return int
     */
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("publisher") String publisher);

    /**
     * 插入博客
     *
     * @param dto dto
     * @return int
     */
    int insert(BlogPublishDTO dto);

    /**
     * 更新博客
     *
     * @param dto dto
     * @return int
     */
    int update(BlogUpdateDTO dto);

    /**
     * 根据主键查找博客
     *
     * @param id id
     * @return {@link Blog}
     */
    Blog selectByPrimaryKey(Integer id);

    /**
     * 增加博客点赞量
     *
     * @param id   id
     * @param incr 增量
     * @return int
     */
    int incrFavorNum(@Param("id") Integer id, @Param("incr") Integer incr);

    /**
     * 增加评论数量
     *
     * @param id   id
     * @param incr 增加
     * @return int
     */
    int incrCommentNum(@Param("id") Integer id, @Param("incr") Integer incr);

    /**
     * 通过id获取作者
     *
     * @param id id
     * @return {@link String}
     */
    String getAuthorById(@Param("id") Integer id);

    /**
     * 得到BlogTinyVOList
     * 可以得到某人发表的博客或某人点赞的博客
     *
     * @param publisher  作者
     * @param liker      点赞者
     * @param startIndex startIndex
     * @param rowNum     rowNum
     * @return {@link List}<{@link BlogTinyVO}>
     */
    List<BlogTinyVO> getTinyList(
        @Param("publisher") String publisher,
        @Param("liker") String liker,
        @Param("startIndex") Integer startIndex,
        @Param("rowNum") Integer rowNum);
}