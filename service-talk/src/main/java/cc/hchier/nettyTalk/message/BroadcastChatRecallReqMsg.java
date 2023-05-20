package cc.hchier.nettyTalk.message;

public class BroadcastChatRecallReqMsg extends Message {
    private Integer id;
    private String from;

    public BroadcastChatRecallReqMsg() {
        super.msgType = 7;
    }
}
