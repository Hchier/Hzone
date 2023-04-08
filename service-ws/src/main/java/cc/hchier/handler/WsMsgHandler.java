package cc.hchier.handler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/8 10:08
 */
public abstract class WsMsgHandler<M> implements Handler{
    public WsMsgHandler() {

    }

    public void handle(Object msg, Map<String, Session> onlineUsers) throws IOException {
        @SuppressWarnings("unchecked")
        M m = (M) msg;
        handle0(m, onlineUsers);
    }

    public abstract void handle0(M msg, Map<String, Session> onlineUsers) throws IOException;
}
