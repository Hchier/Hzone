package cc.hchier.nettyTalk.client.handler;

import cc.hchier.nettyTalk.message.BroadcastChatRespMsg;
import cc.hchier.service.WsService;
import cc.hchier.wsMsgs.BroadcastChatMsg;
import cc.hchier.wsMsgs.WsMsgDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author by Hchier
 * @Date 2023/4/16 13:43
 */
@Slf4j
public class BroadcastChatRespMsgHandler extends SimpleChannelInboundHandler<BroadcastChatRespMsg> {
    private final WsService wsService;

    public BroadcastChatRespMsgHandler(WsService wsService) {
        this.wsService = wsService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BroadcastChatRespMsg msg) {
        BroadcastChatMsg wsMsg = new BroadcastChatMsg()
            .setId(msg.getId())
            .setSender(msg.getFrom())
            .setReceiver(msg.getTo())
            .setContent(msg.getContent())
            .setCreateTime(msg.getCreateTime());

        WsMsgDTO<Object> dto = WsMsgDTO.build(wsMsg);
        log.info("已发送WsMsgDTO：" + dto);
        wsService.sendWsDTO(dto);
    }
}
