package cc.hchier.nettyTalk.client.handler;

import cc.hchier.nettyTalk.message.BroadcastChatRecallRespMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class BroadcastChatRecallRespMsgHandler extends SimpleChannelInboundHandler<BroadcastChatRecallRespMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BroadcastChatRecallRespMsg msg) throws Exception {

    }
}
