package cc.hchier.nettyTalk.message;

public class PrivateChatRecallRespMsg extends Message {
    private Integer id;
    private String from;
    private String to;

    public PrivateChatRecallRespMsg() {
        super.msgType = 10;
    }
}
