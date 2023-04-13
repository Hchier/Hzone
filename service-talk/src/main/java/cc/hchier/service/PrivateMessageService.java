package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.dto.PrivateMsgRecallDTO;
import cc.hchier.vo.ChatUserVO;
import cc.hchier.vo.PrivateMessageVO;

import java.util.List;

/**
 * @author hchier
 */
public interface PrivateMessageService {
    /**
     * 私信入库
     *
     * @param dto dto
     * @return boolean
     */
    boolean add(PrivateChatAddDTO dto);

    /**
     * 查找2人之间的私信
     *
     * @param username1       username1
     * @param username2         username2
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link RestResponse}<{@link List}<{@link PrivateMessageVO}>>
     */
    RestResponse<List<PrivateMessageVO>> getPrivateMessages(String username1, String username2, int startIndex, int rowNum);


    /**
     * 撤回私信
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> recall(PrivateMsgRecallDTO dto);

    /**
     * 设置某人发来的所有私信已读
     *
     * @param from from
     * @param to   to
     * @return {@link RestResponse}<{@link Integer}>
     */
    RestResponse<Integer> setMsgsRead(String from, String to);

    /**
     * 得到ChatUserVOList
     *
     * @param receiver 接收者
     * @return {@link RestResponse}<{@link List}<{@link ChatUserVO}>>
     */
    RestResponse<List<ChatUserVO>> getChatUserVOList( String receiver);

}
