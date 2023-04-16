package cc.hchier.mapper;

import cc.hchier.dto.BroadcastMsgAddDTO;
import cc.hchier.entity.BroadcastMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface BroadcastMessageMapper {
    /**
     * 插入
     *
     * @param dto dto
     * @return int
     */
    int insert(BroadcastMsgAddDTO dto);

    /**
     * 查找列表
     *
     * @param startIndex 开始指数
     * @param rowNum     行num
     * @return {@link List}<{@link BroadcastMessage}>
     */
    List<BroadcastMessage> selectList(@Param("startIndex") Integer startIndex, @Param("rowNum") Integer rowNum);

    /**
     * 撤回
     *
     * @param id   id
     * @param from 从
     * @return int
     */
    int recall(@Param("id") Integer id, @Param("from") String from);
}
