package cc.hchier.nettyTalk.server.handler;

import cc.hchier.nettyTalk.message.BroadcastChatRecallReqMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class BroadcastChatRecallReqMsgHandler extends SimpleChannelInboundHandler<BroadcastChatRecallReqMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BroadcastChatRecallReqMsg msg) throws Exception {

    }
}
