package cc.hchier.service;

import cc.hchier.dto.BroadcastMsgAddDTO;
import cc.hchier.dto.BroadcastMsgAddSuccessDTO;
import cc.hchier.response.RestResponse;
import cc.hchier.vo.BroadcastMsgVO;

import java.util.List;

/**
 * @author hchier
 */
public interface BroadcastMessageService {
    /**
     * 新增
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link BroadcastMsgAddSuccessDTO}>
     */
    RestResponse<BroadcastMsgAddSuccessDTO> add(BroadcastMsgAddDTO dto);

    /**
     * 查找广播消息
     *
     * @param currentUser 当前用户
     * @param startIndex  开始指数
     * @param rowNum      行num
     * @return {@link RestResponse}<{@link List}<{@link BroadcastMsgVO}>>
     */
    RestResponse<List<BroadcastMsgVO>> getBroadcastMsgs(String currentUser, int startIndex, int rowNum);

    /**
     * 撤回广播消息
     *
     * @param id   id
     * @param from 从
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> recall(int id, String from);
}
