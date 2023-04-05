package cc.hchier.nettyTalk.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author by Hchier
 * @Date 2022/10/7 15:25
 * <p>
 * 解析配置文件中的ip地址和port
 */

@Component
public class Properties {
    @Value("${netty.server.ip}")
    public String serverIp;

    @Value("${netty.server.port}")
    public int serverPort;

}


