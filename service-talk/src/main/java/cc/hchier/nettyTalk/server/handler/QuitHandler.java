package cc.hchier.nettyTalk.server.handler;

import cc.hchier.nettyTalk.server.service.ChannelService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author by Hchier
 * @Date 2022/10/5 9:20
 * <p>
 * 客户端退出处理器
 */
@Slf4j
public class QuitHandler extends ChannelInboundHandlerAdapter {
    private final ChannelService channelService;

    public QuitHandler(
        ChannelService channelService) {
        this.channelService = channelService;
    }


    /**
     * 当连接断开时触发: channel().close()
     *
     * @param ctx ctx
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("频道：" + ctx.channel() + "断开连接");
        channelService.unbind(ctx.channel());
        super.channelInactive(ctx);
    }


    /**
     * 客户端被强制停掉后触发
     *
     * @param ctx   ctx
     * @param cause cause
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("频道：" + ctx.channel() + "断开连接，异常：" + cause.toString());
        channelService.unbind(ctx.channel());
        super.exceptionCaught(ctx, cause);
    }
}
