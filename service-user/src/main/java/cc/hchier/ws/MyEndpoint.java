package cc.hchier.ws;

import cc.hchier.configuration.Properties;
import cc.hchier.dto.WsMsgDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by Hchier
 * @Date 2023/4/3 17:59
 */
@ServerEndpoint("/ws/{token}")
@Component
@Slf4j
public class MyEndpoint {

    private static RedisTemplate<String, Object> redisTemplate;
    private static Properties properties;
    private static ObjectMapper objectMapper;

    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    public MyEndpoint() {
    }

    @Autowired
    public MyEndpoint(RedisTemplate<String, Object> redisTemplate, Properties properties, ObjectMapper objectMapper) {
        MyEndpoint.redisTemplate = redisTemplate;
        MyEndpoint.properties = properties;
        MyEndpoint.objectMapper = objectMapper;
    }

    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session, EndpointConfig config) {
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        if (username == null) {
            return;
        }
        onlineUsers.put(username, session);
        log.info("用户：" + username + "上线了");
    }

    public void sendMessage(WsMsgDTO<Object> dto) throws IOException {
        Session session = onlineUsers.get(dto.getReceiver());
        if (session == null) {
            log.info("用户" + dto.getReceiver() + "的session为空，无法发送ws消息：" + dto);
            return;
        }
        log.info("已向用户" + dto.getReceiver() + "发送" + dto);
        session.getBasicRemote().sendText(objectMapper.writeValueAsString(dto));
    }

    public boolean existUser(String username) {
        return onlineUsers.get(username) != null;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("receive message is " + message);
    }

    @OnClose
    public void onClose(@PathParam("token") String token, Session session, CloseReason closeReason) {
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        if (username == null) {
            return;
        }
        onlineUsers.remove(username);
        log.info("用户：" + username + "下线了");
    }

    @OnError
    public void onError(@PathParam("token") String token, Session session, Throwable throwable) {
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        if (username == null) {
            return;
        }
        onlineUsers.remove(username);
        log.info("用户：" + username + "发生异常，强制下线");
    }
}
