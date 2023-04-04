package cc.hchier.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author by Hchier
 * @Date 2023/4/3 19:45
 */
@Configuration
public class WebSocketConfig {

    /**
     * 自动注册使用了@ServerEndpoint的bean
     *
     * @return {@link ServerEndpointExporter}
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
