package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.entity.Notice;
import cc.hchier.vo.NoticeVO;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/23 12:17
 */
public interface NoticeService {
    /**
     * 批量插入通知
     *
     * @param noticeList 通知列表
     * @return int
     */
    int addList(List<Notice> noticeList);

    /**
     * 删除通知
     *
     * @param id       id
     * @param receiver 接收者
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> delete(int id, String receiver);

    /**
     * 查找通知
     *
     * @param receiver   接收者
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link RestResponse}<{@link List}<{@link NoticeVO}>>
     */
    RestResponse<List<NoticeVO>> get(String receiver, int startIndex, int rowNum);
}
