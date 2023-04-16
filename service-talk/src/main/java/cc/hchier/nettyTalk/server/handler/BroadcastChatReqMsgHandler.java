package cc.hchier.nettyTalk.server.handler;

import cc.hchier.nettyTalk.message.BroadcastChatReqMsg;
import cc.hchier.nettyTalk.message.BroadcastChatRespMsg;
import cc.hchier.nettyTalk.server.service.ChannelService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author by Hchier
 * @Date 2023/4/16 13:32
 */
@Slf4j
public class BroadcastChatReqMsgHandler extends SimpleChannelInboundHandler<BroadcastChatReqMsg> {
    private final ChannelService channelService;

    public BroadcastChatReqMsgHandler(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BroadcastChatReqMsg msg) {
        BroadcastChatRespMsg broadcastChatRespMsg = new BroadcastChatRespMsg()
            .setId(msg.getId())
            .setFrom(msg.getFrom())
            .setContent(msg.getContent())
            .setCreateTime(msg.getCreateTime());

        Set<String> onlineUsers = channelService.getOnlineUsers();
        onlineUsers.forEach(username -> {
            //不给自己发
            if (username.equals(msg.getFrom())) {
                return;
            }
            Channel channel = channelService.getChannel(username);
            if (channel != null) {
                broadcastChatRespMsg.setTo(username);
                log.info("已发送BroadcastChatRespMsg：" + broadcastChatRespMsg);
                channel.writeAndFlush(broadcastChatRespMsg);
            }
        });
    }
}
