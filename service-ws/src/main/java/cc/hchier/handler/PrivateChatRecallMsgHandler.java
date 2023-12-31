package cc.hchier.handler;

import cc.hchier.wsMsgs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/8 15:59
 */
@Slf4j
@Component
public class PrivateChatRecallMsgHandler extends WsMsgHandler<PrivateChatRecallMsg> {
    private final ObjectMapper objectMapper;

    public PrivateChatRecallMsgHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle0(WsMsgVO<PrivateChatRecallMsg> vo, Map<String, Session> onlineUsers) throws IOException {
        Session session = onlineUsers.get(vo.getBody().getReceiver());
        if (session == null) {
            log.info("用户" + vo.getBody().getReceiver() + "的session为空，无法发送ws消息：" + vo);
            return;
        }
        log.info("已向用户" + vo.getBody().getReceiver() + "发送ws消息" + vo);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(vo));
    }
}
