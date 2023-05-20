package cc.hchier.nettyTalk.server.handler;

import cc.hchier.nettyTalk.message.PrivateChatRecallReqMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class PrivateChatRecallReqMsgHandler extends SimpleChannelInboundHandler<PrivateChatRecallReqMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatRecallReqMsg msg) throws Exception {

    }
}
