package cc.hchier.consts;

import lombok.AllArgsConstructor;

/**
 * @author hchier
 */

@AllArgsConstructor
public enum FollowType {
    // 关注用户
    USER(1),

    //关注话题
    TOPIC(2);
    private final int code;

    public int getCode() {
        return code;
    }
}
