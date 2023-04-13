package cc.hchier.nettyTalk.client.service;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.PrivateChatAddSuccessDTO;
import cc.hchier.service.PrivateMessageService;
import cc.hchier.nettyTalk.client.handler.PongMsgHandler;
import cc.hchier.nettyTalk.client.handler.PrivateChatRespMsgHandler;
import cc.hchier.nettyTalk.configuration.Properties;
import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.nettyTalk.message.PingMsg;
import cc.hchier.nettyTalk.message.PrivateChatReqMsg;
import cc.hchier.nettyTalk.protocol.MessageCodecSharable;
import cc.hchier.nettyTalk.server.service.ChannelService;
import cc.hchier.service.WsService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author by Hchier
 * @Date 2023/2/25 10:35
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ChannelService channelService;
    private final PrivateMessageService privateMessageService;
    private final MessageCodecSharable messageCodecSharable;

    private final Properties properties;

    private final WsService wsService;

    public ClientServiceImpl(
        @Qualifier("localChannelServiceImpl") ChannelService channelService,
        PrivateMessageService privateMessageService,
        MessageCodecSharable messageCodecSharable,
        Properties properties,
        WsService wsService) {
        this.channelService = channelService;
        this.privateMessageService = privateMessageService;
        this.messageCodecSharable = messageCodecSharable;
        this.properties = properties;
        this.wsService = wsService;
    }


    @Override
    public Channel getChannel(String username) {
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
                            .addLast(new PrivateChatRespMsgHandler(wsService))
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
        log.info("已为用户" + username + "创建频道");
        channelService.bind(channel, username);
        return channel;
    }

    @Override
    public RestResponse<PrivateChatAddSuccessDTO> privateTalk(PrivateChatAddDTO dto) {
        dto.setCreateTime(new Date());
        if (!privateMessageService.add(dto)) {
            return RestResponse.fail();
        }

        Channel channel = getChannel(dto.getFrom());
        String dateNowStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getCreateTime());
        channel.writeAndFlush(
            new PrivateChatReqMsg()
                .setId(dto.getId())
                .setFrom(dto.getFrom())
                .setTo(dto.getTo())
                .setContent(dto.getContent())
                .setCreateTime(dateNowStr));

        return RestResponse.ok(
            new PrivateChatAddSuccessDTO()
                .setId(dto.getId())
                .setFrom(dto.getFrom())
                .setTo(dto.getTo())
                .setContent(dto.getContent())
                .setCreateTime(dateNowStr));
    }

    @Override
    public void closeChannel(String username) {
        Channel channel = channelService.getChannel(username);
        if (channel == null) {
            return;
        }
        channelService.unbind(channel);
        channel.close();
    }
}
