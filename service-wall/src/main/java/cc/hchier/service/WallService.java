package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.WallAddDTO;
import cc.hchier.vo.WallVO;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/20 10:08
 */
public interface WallService {
    /**
     * 新增留言
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<WallVO> add(WallAddDTO dto);

    /**
     * 隐藏留言
     * 只能隐藏自己收到的留言
     *
     * @param id          id
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> hidden(int id, String currentUser);

    /**
     * 删除留言
     * 只能删除自己发表的留言
     *
     * @param id          id
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> delete(int id, String currentUser);

    /**
     * 得到
     * 得到某人的留言墙
     *
     * @param currentUser 当前用户
     * @param commentee   被留言者
     * @param startIndex  起始下标
     * @param rowNum      行num
     * @return {@link RestResponse}<{@link List}<{@link WallVO}>>
     */
    RestResponse<List<WallVO>> get(String currentUser, String commentee, int startIndex, int rowNum);
}
