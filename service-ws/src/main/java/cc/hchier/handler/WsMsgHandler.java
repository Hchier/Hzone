package cc.hchier.handler;

import cc.hchier.wsMsgs.WsMsgTypeMap;
import cc.hchier.wsMsgs.WsMsgVO;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/8 10:08
 */
public abstract class WsMsgHandler<M> implements Handler {
    public WsMsgHandler() {

    }

    public void handle(Object msg, Map<String, Session> onlineUsers) throws IOException {
        WsMsgVO<M> vo = (WsMsgVO<M>) WsMsgVO.build(WsMsgTypeMap.TYPENAME_CODE_MAP.get(msg.getClass().getTypeName()), msg);
        handle0(vo, onlineUsers);
    }

    public abstract void handle0(WsMsgVO<M> vo, Map<String, Session> onlineUsers) throws IOException;
}
