package cc.hchier.mapper;

import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.entity.PrivateMessage;
import cc.hchier.vo.ChatUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/25 14:27
 */
@Mapper
public interface PrivateMessageMapper {
    /**
     * 插入
     *
     * @param dto dto
     * @return int
     */
    int insert(PrivateChatAddDTO dto);

    /**
     * 撤回
     *
     * @param id   id
     * @param from 从
     * @return int
     */
    int recall(@Param("id") Integer id, @Param("from") String from);

    /**
     * 查找2人之间的聊天记录
     *
     * @param username1       username1
     * @param username2         username2
     * @param startIndex 开始指数
     * @param rowNum     行num
     * @return {@link List}<{@link PrivateMessage}>
     */
    List<PrivateMessage> select(
        @Param("username1") String username1,
        @Param("username2") String username2,
        @Param("startIndex") Integer startIndex,
        @Param("rowNum") Integer rowNum);

    /**
     * 设置某人发送的所有消息已读
     *
     * @param from 从
     * @param to   来
     * @return int
     */
    int setMsgsRead(@Param("from") String from, @Param("to") String to);

    /**
     * 得到ChatUserVOList
     *
     * @param receiver 接收者
     * @return {@link List}<{@link ChatUserVO}>
     */
    List<ChatUserVO> getChatUserVOList(@Param("receiver") String receiver);
}
