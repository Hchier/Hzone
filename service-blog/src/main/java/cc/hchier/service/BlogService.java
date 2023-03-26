package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.BlogPublishDTO;
import cc.hchier.dto.BlogUpdateDTO;
import cc.hchier.entity.Blog;
import cc.hchier.vo.BlogTinyVO;
import cc.hchier.vo.BlogVO;
import io.seata.core.exception.TransactionException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author hchier
 */
public interface BlogService {

    /**
     * 将{@link Blog}转为{@link BlogVO}。
     * 但是{@link BlogVO}中的favored和updatePermission在这儿搞不定
     *
     * @param blog 博客
     * @return {@link BlogVO}
     */
    static BlogVO map(Blog blog) {
        return new BlogVO()
            .setId(blog.getId())
            .setPublisher(blog.getPublisher())
            .setTitle(blog.getTitle())
            .setContent(blog.getContent())
            .setFavorNum(blog.getFavorNum())
            .setCommentNum(blog.getCommentNum())
            .setRewardNum(blog.getRewardNum())
            .setSelfVisible(blog.getSelfVisible())
            .setHidden(blog.getHidden())
            .setCommentForbidden(blog.getCommentForbidden())
            .setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(blog.getUpdateTime()))
            .setTopic(blog.getTopic());
    }

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


    /**
     * 得到某主题下的博客
     *
     * @param topic       主题
     * @param startIndex  开始指数
     * @param rowNum      行num
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link List}<{@link BlogVO}>>
     */
    RestResponse<List<BlogVO>> getListByTopic(String topic, int startIndex, int rowNum, String currentUser);

    /**
     * 得到用于首页展示的列表
     *
     * @param currentUser 当前用户
     * @return {@link RestResponse}<{@link List}<{@link BlogVO}>>
     */
    RestResponse<List<BlogVO>> getHomeList(String currentUser);
}
