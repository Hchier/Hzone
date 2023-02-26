package cc.hchier.talk.server.service;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理接口
 *
 * @author hchier
 */
public abstract class ChannelService {

    protected Map<String, Channel> usernameChannelMap;
    protected Map<Channel, String> channelUsernameMap;
    protected Map<Channel, Map<String, Object>> channelAttributesMap;

    /**
     * 绑定会话
     *
     * @param channel  哪个 channel 要绑定会话
     * @param username 会话绑定用户
     */
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUsernameMap.put(channel, username);
        channelAttributesMap.put(channel, new ConcurrentHashMap<>(8));
    }

    /**
     * 解绑会话
     *
     * @param channel 哪个 channel 要解绑会话
     */
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannelMap.remove(username);
        channelAttributesMap.remove(channel);
    }

    /**
     * 获取属性
     *
     * @param channel 哪个 channel
     * @param name    属性名
     * @return 属性值
     */
    public Object getAttribute(Channel channel, String name) {
        return channelAttributesMap.get(channel).get(name);
    }

    /**
     * 设置属性
     *
     * @param channel 哪个 channel
     * @param name    属性名
     * @param value   属性值
     */
    public void setAttribute(Channel channel, String name, Object value) {
        channelAttributesMap.get(channel).put(name, value);
    }

    /**
     * 根据用户名获取 channel
     *
     * @param username 用户名
     * @return channel
     */
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    /**
     * 根据channel获取用户名
     *
     * @param channel 通道
     * @return {@link String}
     */
    public String getName(Channel channel) {
        return channelUsernameMap.get(channel);
    }

    public Set<String> getOnlineUsers() {
        return usernameChannelMap.keySet();
    }
}
