package cc.hchier.handler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author hchier
 */
public interface Handler {
    void handle(Object msg, Map<String, Session> onlineUsers) throws IOException;
}
