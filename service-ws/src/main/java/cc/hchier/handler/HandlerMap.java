package cc.hchier.handler;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by Hchier
 * @Date 2023/4/8 10:32
 */

@Component
public class HandlerMap {
    private final Map<String, Handler> handlerMap = new ConcurrentHashMap<>();

    public HandlerMap(
        PrivateChatMsgHandler privateChatMsgHandler,
        PrivateChatRecallMsgHandler privateChatRecallMsgHandler,
        BroadcastChatMsgHandler broadcastChatMsgHandler
    ) {

        //新的消息处理器需要先注册
        List<Handler> handlerList = new ArrayList<>();
        handlerList.add(privateChatMsgHandler);
        handlerList.add(privateChatRecallMsgHandler);
        handlerList.add(broadcastChatMsgHandler);


        for (Handler handler : handlerList) {
            String typeName = handler.getClass().getGenericSuperclass().getTypeName();
            int first = typeName.indexOf("<") + 1, last = typeName.lastIndexOf(">");
            String msgTypeName = typeName.substring(first, last);
            handlerMap.put(msgTypeName, handler);
        }
    }

    public Handler getHandler(String typeName) {
        return handlerMap.get(typeName);
    }
}
