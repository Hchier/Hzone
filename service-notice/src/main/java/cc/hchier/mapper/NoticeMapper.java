package cc.hchier.mapper;

import cc.hchier.entity.Notice;
import cc.hchier.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface NoticeMapper {
    /**
     * 删除通知
     *
     * @param id       id
     * @param receiver 接收者
     * @return int
     */
    int delete(@Param("id") Integer id, @Param("receiver") String receiver);

    /**
     * 批量插入
     *
     * @param noticeList 通知列表
     * @return int
     */
    int insertList(@Param("noticeList") List<Notice> noticeList);

    /**
     * 插入
     *
     * @param record 记录
     * @return int
     */
    int insert(Notice record);

    /**
     * 查找通知
     *
     * @param receiver   接收者
     * @param startIndex 开始index
     * @param rowNum     行num
     * @return {@link List}<{@link NoticeVO}>
     */
    List<NoticeVO> selectByReceiver(@Param("receiver") String receiver, @Param("startIndex") Integer startIndex, @Param("rowNum") Integer rowNum);

}