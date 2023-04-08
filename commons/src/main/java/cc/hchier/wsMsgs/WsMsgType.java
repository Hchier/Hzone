package cc.hchier.wsMsgs;

import lombok.AllArgsConstructor;

/**
 * @author by Hchier
 * @Date 2023/4/3 19:17
 */
@AllArgsConstructor
public enum WsMsgType {
    /**
     * 通知数量+1
     */
    NoticeNumIncr(1, null),
    /**
     * 私信
     */
    PrivateChatMsg(2, PrivateChatMsg.class),

    /**
     * 私信撤回
     */
    PrivateChatRecallMsg(3, PrivateChatRecallMsg.class);

    private final int code;

    private final Class<? extends WsMsg> msgClass;

    public int getCode() {
        return code;
    }

    public Class<? extends WsMsg> getMsgClass() {
        return msgClass;
    }
}
