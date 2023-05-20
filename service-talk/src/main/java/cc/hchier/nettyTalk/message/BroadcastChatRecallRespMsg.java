package cc.hchier.nettyTalk.message;

public class BroadcastChatRecallRespMsg extends Message {
    private Integer id;
    private String from;

    public BroadcastChatRecallRespMsg() {
        super.msgType = 8;
    }
}
