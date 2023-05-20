package cc.hchier.nettyTalk.message;

public class PrivateChatRecallReqMsg extends Message {
    private Integer id;
    private String from;
    private String to;

    public PrivateChatRecallReqMsg() {
        super.msgType = 9;
    }
}
