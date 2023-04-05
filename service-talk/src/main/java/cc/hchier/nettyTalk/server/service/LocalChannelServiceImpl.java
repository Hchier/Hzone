package cc.hchier.nettyTalk.server.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hchier
 */
@Component
public class LocalChannelServiceImpl extends ChannelService {

    public LocalChannelServiceImpl(){
        super.usernameChannelMap = new ConcurrentHashMap<>();
        super.channelUsernameMap = new ConcurrentHashMap<>();
        super.channelAttributesMap = new ConcurrentHashMap<>();
    }
}
