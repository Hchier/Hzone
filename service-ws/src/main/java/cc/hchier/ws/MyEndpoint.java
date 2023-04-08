package cc.hchier.ws;

import cc.hchier.configuration.Properties;
import cc.hchier.handler.Handler;
import cc.hchier.handler.Handlers;
import cc.hchier.wsMsgs.WsMsg;
import cc.hchier.wsMsgs.WsMsgDTO;
import cc.hchier.wsMsgs.WsMsgType;
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

    private static final Map<String, Session> ONLINE_USERS = new ConcurrentHashMap<>();

    private static Handlers handlers;


    public MyEndpoint() {
    }

    @Autowired
    public MyEndpoint(RedisTemplate<String, Object> redisTemplate, Properties properties, ObjectMapper objectMapper, Handlers handlers) {
        MyEndpoint.redisTemplate = redisTemplate;
        MyEndpoint.properties = properties;
        MyEndpoint.objectMapper = objectMapper;
        MyEndpoint.handlers = handlers;
    }

    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session, EndpointConfig config) {
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        //token无效
        if (username == null) {
            return;
        }
        if (ONLINE_USERS.get(username) != null) {
            //todo web端能不能共享ws
            return;
        }
        ONLINE_USERS.put(username, session);
        log.info("用户：" + username + "上线了");
    }

    public void sendMessage(WsMsgDTO<Object> dto) throws IOException {
        String typeName = "";
        Class<? extends WsMsg> msgClass = null;
        for (WsMsgType type : WsMsgType.values()) {
            if (type.getCode() == dto.getType()) {
                typeName = type.getMsgClass().getTypeName();
                msgClass = type.getMsgClass();
                break;
            }
        }
        Handler handler = handlers.handlerMap.get(typeName);
        if (handler == null) {
            log.error("对应的消息处理器缺失：" + typeName);
            return;
        }
        WsMsg msg = objectMapper.readValue(objectMapper.writeValueAsString(dto.getBody()), msgClass);
        handler.handle(msg, ONLINE_USERS);
    }

    public boolean existUser(String username) {
        return ONLINE_USERS.get(username) != null;
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
        ONLINE_USERS.remove(username);
        log.info("用户：" + username + "下线了");
    }

    @OnError
    public void onError(@PathParam("token") String token, Session session, Throwable throwable) {
        String username = (String) redisTemplate.opsForHash().get(properties.hashForToken, token);
        if (username == null) {
            return;
        }
        ONLINE_USERS.remove(username);
        log.info("用户：" + username + "发生异常，强制下线");
    }
}
