package cc.hchier.nettyTalk.server.handler;

import cc.hchier.nettyTalk.message.PongMsg;
import cc.hchier.nettyTalk.message.PrivateChatReqMsg;
import cc.hchier.nettyTalk.message.PrivateChatRespMsg;
import cc.hchier.nettyTalk.server.service.ChannelService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by Hchier
 * @Date 2023/2/25 16:59
 */

public class PrivateChatReqMsgHandler extends SimpleChannelInboundHandler<PrivateChatReqMsg> {
    private final ChannelService channelService;

    public PrivateChatReqMsgHandler(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatReqMsg msg) {
        Channel channelTo = channelService.getChannel(msg.getTo());
        System.out.println("channelTo: " + channelTo);
        //用户在线
        if (channelTo != null) {
            channelTo.writeAndFlush(
                new PrivateChatRespMsg()
                    .setId(msg.getId())
                    .setFrom(msg.getFrom())
                    .setTo(msg.getTo())
                    .setContent(msg.getContent())
                    .setCreateTime(msg.getCreateTime())
            );
        } else {
            ctx.channel().writeAndFlush(new PongMsg().setSuccess(false).setReason("用户不在线"));
        }
    }
}
