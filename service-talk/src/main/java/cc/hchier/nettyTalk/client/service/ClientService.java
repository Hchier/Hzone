package cc.hchier.nettyTalk.client.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.PrivateChatAddDTO;
import io.netty.channel.Channel;

/**
 * @author hchier
 */
public interface ClientService {

    /**
     * 返回当前用户的channel。
     * 若不存在，则创建并返回。
     *
     * @param username 用户名
     * @return {@link Channel}
     */
    Channel getChannel(String username);

    /**
     * 发送私信
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> privateTalk(PrivateChatAddDTO dto);

    /**
     * 关闭频道
     *
     * @param username 用户名
     */
    void closeChannel(String username);
}
