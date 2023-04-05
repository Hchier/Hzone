package cc.hchier.nettyTalk.client.handler;

import cc.hchier.nettyTalk.message.PongMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by Hchier
 * @Date 2023/2/26 10:36
 */
public class PongMsgHandler extends SimpleChannelInboundHandler<PongMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PongMsg msg) {
        System.out.println("pong msg: " + msg);
    }
}
