package cc.hchier.talk.client.service;

import cc.hchier.RestResponse;
import cc.hchier.service.PrivateMessageService;
import cc.hchier.talk.client.handler.PongMsgHandler;
import cc.hchier.talk.client.handler.PrivateChatRespMsgHandler;
import cc.hchier.talk.configuration.Properties;
import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.talk.message.PingMsg;
import cc.hchier.talk.message.PrivateChatReqMsg;
import cc.hchier.talk.protocol.MessageCodecSharable;
import cc.hchier.talk.server.service.ChannelService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author by Hchier
 * @Date 2023/2/25 10:35
 */
@Service
public class ClientServiceImpl implements ClientService {
    private final ChannelService channelService;
    private final PrivateMessageService privateMessageService;
    private final MessageCodecSharable messageCodecSharable;

    private final Properties properties;

    public ClientServiceImpl(@Qualifier("localChannelServiceImpl") ChannelService channelService, PrivateMessageService privateMessageService, MessageCodecSharable messageCodecSharable, Properties properties) {
        this.channelService = channelService;
        this.privateMessageService = privateMessageService;
        this.messageCodecSharable = messageCodecSharable;
        this.properties = properties;
    }


    @Override
    public Channel createChannel(String username) {
        Channel channel = null;
        if ((channel = channelService.getChannel(username)) != null) {
            return channel;
        }
        NioEventLoopGroup group = new NioEventLoopGroup();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        try {
            channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(1024, 7, 4, 0, 0))
                            .addLast(messageCodecSharable)
                            .addLast(new PrivateChatRespMsgHandler())
                            .addLast(new PongMsgHandler());
                    }
                })
                .connect(new InetSocketAddress(
                    properties.serverIp,
                    properties.serverPort))
                .sync()
                .channel();

            Channel finalChannel = channel;
            pool.submit(() -> {
                try {
                    finalChannel.closeFuture().sync();
                    group.shutdownGracefully();
                    pool.shutdownNow();
                    //log.info("频道关闭");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert channel != null;
        channel.writeAndFlush(
            new PingMsg()
                .setUsername(username)
        );
        channelService.bind(channel, username);
        return channel;
    }

    @Override
    public RestResponse<Object> privateTalk(PrivateChatAddDTO dto) {
        dto.setCreateTime(new Date());
        Channel channel = createChannel(dto.getFrom());

        channel.writeAndFlush(
            new PrivateChatReqMsg()
                .setFrom(dto.getFrom())
                .setTo(dto.getTo())
                .setContent(dto.getContent())
                .setCreateTime(new Date()));

        if (!privateMessageService.add(dto)) {
            return RestResponse.fail();
        }
        return RestResponse.ok();
    }
}
