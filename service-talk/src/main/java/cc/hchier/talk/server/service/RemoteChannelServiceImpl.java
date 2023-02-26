package cc.hchier.talk.server.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by Hchier
 * @Date 2023/2/26 11:16
 */
@Component
public class RemoteChannelServiceImpl extends ChannelService {

    public RemoteChannelServiceImpl() {
        super.usernameChannelMap = new ConcurrentHashMap<>();
        super.channelUsernameMap = new ConcurrentHashMap<>();
        super.channelAttributesMap = new ConcurrentHashMap<>();
    }
}