package cc.hchier.talk.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/2/25 15:10
 */
public class MsgType {
    private static final Map<Byte, Class<? extends Message>> TYPE_MAP = new HashMap<Byte, Class<? extends Message>>() {{
        put((byte) 0, SysRespMsg.class);
        put((byte) 1, PrivateChatReqMsg.class);
        put((byte) 2, PrivateChatRespMsg.class);
        put((byte) 3, PingMsg.class);
        put((byte) 4, PongMsg.class);
    }};

    public static Class<? extends Message> getClassType(byte i) {
        return TYPE_MAP.get(i);
    }
}
