package cc.hchier.nettyTalk.server;


import cc.hchier.nettyTalk.server.handler.BroadcastChatReqMsgHandler;
import cc.hchier.nettyTalk.server.handler.PingMsgHandler;
import cc.hchier.nettyTalk.server.handler.PrivateChatReqMsgHandler;
import cc.hchier.nettyTalk.server.handler.QuitHandler;
import cc.hchier.nettyTalk.server.service.ChannelService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import cc.hchier.nettyTalk.configuration.Properties;
import cc.hchier.nettyTalk.protocol.MessageCodecSharable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author by Hchier
 * @Date 2022/10/4 17:15
 */
@Slf4j
@Component
public class ChatServer {

    private final Properties properties;
    private final ChannelService channelService;

    public ChatServer(Properties properties, @Qualifier("remoteChannelServiceImpl") ChannelService channelService) {
        this.properties = properties;
        this.channelService = channelService;
    }


    public void listen() {
        new ServerBootstrap()
            .group(new NioEventLoopGroup(), new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline()
                        .addLast(new LoggingHandler(LogLevel.DEBUG))
                        .addLast(new LengthFieldBasedFrameDecoder(1024, 7, 4, 0, 0))
                        .addLast(new MessageCodecSharable())
                        .addLast(new PingMsgHandler(channelService))
                        .addLast(new PrivateChatReqMsgHandler(channelService))
                        .addLast(new BroadcastChatReqMsgHandler(channelService))
                        .addLast(new QuitHandler(channelService));
                }
            }).bind(properties.serverPort);
    }
}
