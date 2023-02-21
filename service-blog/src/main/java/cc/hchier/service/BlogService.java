package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.vo.BlogVO;

/**
 * @author hchier
 */
public interface BlogService {
    /**
     * 删除博客
     *
     * @param id        id
     * @param publisher 出版商
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> delete(int id, String publisher);

    /**
     * 发布博客
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Integer}>
     */
    RestResponse<Integer> publish(BlogPublishDTO dto);

    /**
     * 查找博客
     *
     * @param id          id
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link BlogVO}>
     */
    RestResponse<Object> get(int id, String currentUser);

    /**
     * 更新博客
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> update(BlogUpdateDTO dto);
}
