package cc.hchier.handler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import cc.hchier.wsMsgs.WsMsgType;
import cc.hchier.wsMsgs.WsMsg;

/**
 * @author hchier
 * <br>
 * 增加新的消息处理器：
 * <br>
 * 1, 继承{@link WsMsg}，添加新的消息类型 <br>
 * 2, 继承{@link WsMsgHandler}，添加新的处理器 <br>
 * 3, {@link WsMsgType}增加新类型 <br>
 * 4, 将处理器注册到{@link Handlers}中 <br>
 */
public interface Handler {
    void handle(Object msg, Map<String, Session> onlineUsers) throws IOException;
}
