package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.vo.BlogTinyVO;
import cc.hchier.vo.BlogVO;
import io.seata.core.exception.TransactionException;

import java.util.List;

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
     * @throws TransactionException 事务异常
     */
    RestResponse<Integer> publish(BlogPublishDTO dto) throws TransactionException;

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

    /**
     * 通过id获取作者
     *
     * @param id id
     * @return {@link RestResponse}<{@link String}>
     */
    RestResponse<String> getAuthorById(int id);

    /**
     * 发布列表
     *
     * @param publisher  出版商
     * @param startIndex 开始指数
     * @param rowNum     行num
     * @return {@link RestResponse}<{@link List}<{@link BlogTinyVO}>>
     */
    RestResponse<List<BlogTinyVO>> getPublishedList(String publisher, int startIndex, int rowNum);

    /**
     * 点赞列表
     *
     * @param liker      喜欢人
     * @param startIndex 开始指数
     * @param rowNum     行num
     * @return {@link RestResponse}<{@link List}<{@link BlogTinyVO}>>
     */
    RestResponse<List<BlogTinyVO>> getFavoredList(String liker, int startIndex, int rowNum);

}
