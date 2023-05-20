package cc.hchier.handler;

import cc.hchier.wsMsgs.WsMsgTypeMap;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author hchier
 * <br>
 * 当出现新类型的ws消息时：
 * <br>
 * 1, 在{@link cc.hchier.wsMsgs}下定义新的消息类型。<br>
 * 2, {@link WsMsgTypeMap}增加新的消息类型。<br>
 * 3, 继承{@link WsMsgHandler}，添加新的处理器，用于处理第一步定义的消息类型。<br>
 * 4, 将处理器注册到{@link HandlerMap}中。<br>
 */
public interface Handler {
    /**
     * 处理消息
     *
     * @param msg         消息
     * @param onlineUsers 在线用户
     * @throws IOException ioexception
     */
    void handle(Object msg, Map<String, Session> onlineUsers) throws IOException;
}
