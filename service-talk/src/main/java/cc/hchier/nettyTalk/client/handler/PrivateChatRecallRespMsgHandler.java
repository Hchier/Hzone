package cc.hchier.nettyTalk.client.handler;

import cc.hchier.nettyTalk.message.PrivateChatRecallRespMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class PrivateChatRecallRespMsgHandler extends SimpleChannelInboundHandler<PrivateChatRecallRespMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatRecallRespMsg msg) throws Exception {

    }
}
