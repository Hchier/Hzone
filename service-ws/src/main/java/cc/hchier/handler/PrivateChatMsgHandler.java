package cc.hchier.handler;

import cc.hchier.wsMsgs.PrivateChatMsg;
import cc.hchier.wsMsgs.WsMsgDTO;
import cc.hchier.wsMsgs.WsMsgTypeMap;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PrivateChatMsgHandler extends WsMsgHandler<PrivateChatMsg> {
    private final ObjectMapper objectMapper;

    public PrivateChatMsgHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle0(PrivateChatMsg msg, Map<String, Session> onlineUsers) throws IOException {
        Session session = onlineUsers.get(msg.getReceiver());
        if (session == null) {
            log.info("用户" + msg.getReceiver() + "的session为空，无法发送ws消息：" + msg);
            return;
        }
        WsMsgDTO<PrivateChatMsg> dto = WsMsgDTO.build(WsMsgTypeMap.CLASS_CODE_MAP.get(msg.getClass()), msg);
        log.info("已向用户" + msg.getReceiver() + "发送ws消息" + dto);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(dto));
    }
}
