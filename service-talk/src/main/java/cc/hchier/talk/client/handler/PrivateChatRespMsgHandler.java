package cc.hchier.talk.client.handler;

import cc.hchier.talk.message.PrivateChatRespMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by Hchier
 * @Date 2023/2/25 18:12
 */

public class PrivateChatRespMsgHandler extends SimpleChannelInboundHandler<PrivateChatRespMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatRespMsg msg) {
        System.out.println(msg.toString());
        //todo
    }
}
