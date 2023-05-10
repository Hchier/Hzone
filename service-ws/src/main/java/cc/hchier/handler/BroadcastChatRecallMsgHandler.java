package cc.hchier.handler;

import cc.hchier.wsMsgs.BroadcastChatRecallMsg;
import cc.hchier.wsMsgs.WsMsgVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/5/10 10:07
 */
@Component
@Slf4j
public class BroadcastChatRecallMsgHandler extends WsMsgHandler<BroadcastChatRecallMsg> {
    private final ObjectMapper objectMapper;

    public BroadcastChatRecallMsgHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle0(WsMsgVO<BroadcastChatRecallMsg> vo, Map<String, Session> onlineUsers) throws IOException {
        for (Map.Entry<String, Session> entry : onlineUsers.entrySet()) {
            String username = entry.getKey();
            Session session = entry.getValue();
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(vo));
            log.info("已向用户" + username + "发送ws消息" + vo);
        }
    }
}
