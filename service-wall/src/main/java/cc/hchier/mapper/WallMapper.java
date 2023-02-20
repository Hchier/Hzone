package cc.hchier.mapper;

import cc.hchier.dto.WallAddDTO;
import cc.hchier.entity.Wall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface WallMapper {
    /**
     * 删除留言
     *
     * @param id          id
     * @param currentUser 当前用户
     * @return int
     */
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("currentUser") String currentUser);

    /**
     * 插入留言
     *
     * @param record 记录
     * @return int
     */
    int insert(WallAddDTO record);

    /**
     * 隐藏留言
     *
     * @param id          id
     * @param currentUser 当前用户
     * @return int
     */
    int hiddenByPrimaryKey(@Param("id") Integer id, @Param("currentUser") String currentUser);

    /**
     * 被选择
     * 查找某人的留言墙留言
     *
     * @param username   用户名
     * @param startIndex 起始位置
     * @param rowNum     记录条数
     * @return {@link List}<{@link Wall}>
     */
    List<Wall> selectByCommentee(@Param("username") String username, @Param("startIndex") Integer startIndex, @Param("rowNum") Integer rowNum);
}
