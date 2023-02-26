package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.PrivateChatAddDTO;
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
     * @param from       from
     * @param to         to
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link RestResponse}<{@link List}<{@link PrivateMessageVO}>>
     */
    RestResponse<List<PrivateMessageVO>> getPrivateMessages(String from, String to, int startIndex, int rowNum);

    /**
     * 撤回私信
     *
     * @param id   id
     * @param from 发送者
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> recall(int id, String from);

    /**
     * 设置某人发来的所有私信已读
     *
     * @param from from
     * @param to   to
     * @return {@link RestResponse}<{@link Integer}>
     */
    RestResponse<Integer> setMsgsRead(String from, String to);
}
