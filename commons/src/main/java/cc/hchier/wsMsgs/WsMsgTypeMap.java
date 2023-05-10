package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/10 14:08
 */
public class WsMsgTypeMap {
    @AllArgsConstructor
    enum WsMsgType {
        /**
         * 通知数量+1
         */
        NoticeNumIncr(1, NoticeNumIncrMsg.class),
        /**
         * 私信
         */
        PrivateChatMsg(2, PrivateChatMsg.class),

        /**
         * 私信撤回
         */
        PrivateChatRecallMsg(3, PrivateChatRecallMsg.class),

        /**
         * 广播聊天
         */
        BroadcastChatMsg(4, BroadcastChatMsg.class),

        /**
         * 广播聊天信息撤回
         */
        BroadcastChatRecallMsg(5, BroadcastChatRecallMsg.class);

        private final int code;

        private final Class<?> msgClass;

        public int getCode() {
            return code;
        }

        public Class<?> getMsgClass() {
            return msgClass;
        }
    }

    public final static Map<String, Integer> TYPENAME_CODE_MAP = new HashMap<>();

    static {
        WsMsgType[] arr = WsMsgType.values();
        for (WsMsgType item : arr) {
            TYPENAME_CODE_MAP.put(item.getMsgClass().getTypeName(), item.getCode());
        }
    }
}
