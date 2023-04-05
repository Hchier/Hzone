package cc.hchier.task;

import cc.hchier.nettyTalk.server.ChatServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author by Hchier
 * @Date 2023/2/25 14:43
 */
@Component
public class PreparationTask implements ApplicationRunner {
    private final ChatServer chatServer;

    public PreparationTask(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void run(ApplicationArguments args) {
        chatServer.listen();
    }
}
