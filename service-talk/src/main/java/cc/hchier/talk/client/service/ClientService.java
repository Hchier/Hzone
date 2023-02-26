package cc.hchier.talk.client.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.PrivateChatAddDTO;
import io.netty.channel.Channel;

/**
 * @author hchier
 */
public interface ClientService {

    /**
     * 若该用户不存在channel，则创建channel
     *
     * @param username 用户名
     * @return {@link Channel}
     */
    Channel createChannel(String username);

    /**
     * 发送私信
     *
     * @param dto dto
     * @return {@link RestResponse}<{@link Object}>
     */
    RestResponse<Object> privateTalk(PrivateChatAddDTO dto);


}
