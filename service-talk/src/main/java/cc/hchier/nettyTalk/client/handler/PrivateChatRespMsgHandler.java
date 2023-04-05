package cc.hchier.nettyTalk.client.handler;

import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.service.UserService;
import cc.hchier.nettyTalk.message.PrivateChatRespMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author by Hchier
 * @Date 2023/2/25 18:12
 */

public class PrivateChatRespMsgHandler extends SimpleChannelInboundHandler<PrivateChatRespMsg> {
    private final UserService userService;

    public PrivateChatRespMsgHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PrivateChatRespMsg msg) {
        userService.sendPrivateMsg(new PrivateChatAddDTO()
            .setId(msg.getId())
            .setFrom(msg.getFrom())
            .setTo(msg.getTo())
            .setContent(msg.getContent())
            .setCreateTime(msg.getCreateTime()));
    }
}
