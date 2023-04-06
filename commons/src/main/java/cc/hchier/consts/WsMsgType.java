package cc.hchier.consts;

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
    NoticeNumIncr(1),
    /**
     * 私信
     */
    PrivateChatMsg(2),

    /**
     * 私信撤回
     */
    MsgRecall(3);

    private final int code;

    public int getCode() {
        return code;
    }
}
