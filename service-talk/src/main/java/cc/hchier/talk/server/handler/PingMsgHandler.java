package cc.hchier.talk.server.handler;

import cc.hchier.talk.message.PingMsg;
import cc.hchier.talk.message.PongMsg;
import cc.hchier.talk.server.service.ChannelService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by Hchier
 * @Date 2023/2/26 10:14
 */
public class PingMsgHandler extends SimpleChannelInboundHandler<PingMsg> {
    private final ChannelService channelService;

    public PingMsgHandler(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMsg msg) {
        channelService.bind(ctx.channel(), msg.getUsername());
        ctx.channel().writeAndFlush(
            new PongMsg()
                .setSuccess(true)
        );
    }
}
