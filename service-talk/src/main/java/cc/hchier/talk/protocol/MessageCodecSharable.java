package cc.hchier.talk.protocol;

import cc.hchier.talk.message.MsgType;
import cc.hchier.talk.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author by Hchier
 */
@Component
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
        ByteBuf buf = ctx.alloc().buffer();
        //魔数 6字节
        buf.writeBytes("hchier".getBytes(StandardCharsets.UTF_8));

        //消息类型 1字节
        buf.writeByte(msg.getMsgType());

        byte[] bytes = Serializer.Algorithm.Gson.serialize(msg);
        assert bytes != null;
        //正文长度 4字节
        buf.writeInt(bytes.length);

        //消息正文
        buf.writeBytes(bytes);

        out.add(buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //魔数 6字节
        byte[] magicNumBytes = new byte[6];
        in.readBytes(magicNumBytes, 0, 6);
        String magicNum = new String(magicNumBytes);

        //消息类型 1字节
        byte messageType = in.readByte();

        //正文长度 4字节
        int length = in.readInt();

        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        //消息正文
        Message message = Serializer.Algorithm.Gson.deserialize(MsgType.getClassType(messageType), bytes);
        out.add(message);
    }
}
