package cc.hchier.handler;

import cc.hchier.wsMsgs.BroadcastChatMsg;
import cc.hchier.wsMsgs.WsMsgVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/16 13:47
 */
@Slf4j
@Component
public class BroadcastChatMsgHandler extends WsMsgHandler<BroadcastChatMsg> {
    private final ObjectMapper objectMapper;

    public BroadcastChatMsgHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle0(WsMsgVO<BroadcastChatMsg> vo, Map<String, Session> onlineUsers) throws IOException {
        Session session = onlineUsers.get(vo.getBody().getReceiver());
        if (session == null) {
            log.info("用户" + vo.getBody().getReceiver() + "的session为空，无法发送ws消息：" + vo);
            return;
        }
        log.info("已发送WsMsgVO：" + vo);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(vo));
    }
}
