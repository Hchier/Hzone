package cc.hchier.service;

import cc.hchier.RestResponse;

/**
 * @author hchier
 */
public interface TopicService {
    /**
     * 新增话题
     * 先判断是否存在，若存在，则直接返回ok
     *
     * @param name 名字
     */
    RestResponse add(String name);

    /**
     * 查找话题
     * 存在返回ok，否则返回fail
     *
     * @param name 名字
     * @return {@link RestResponse}
     */
    RestResponse get(String name);
}
