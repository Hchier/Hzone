package cc.hchier.handler;

import cc.hchier.wsMsgs.PrivateChatMsg;
import cc.hchier.wsMsgs.WsMsgTypeMap;
import cc.hchier.wsMsgs.WsMsgVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/4/8 10:13
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class PrivateChatMsgHandler extends WsMsgHandler<PrivateChatMsg> {
    private final ObjectMapper objectMapper;

    public PrivateChatMsgHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void handle0(WsMsgVO<PrivateChatMsg> vo, Map<String, Session> onlineUsers)  {
        Session session = onlineUsers.get(vo.getBody().getReceiver());
        if (session == null) {
            log.info("用户" + vo.getBody().getReceiver() + "的session为空，无法发送ws消息：" + vo);
            return;
        }
        log.info("已向用户" + vo.getBody().getReceiver() + "发送ws消息" + vo);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(vo));
    }
}
