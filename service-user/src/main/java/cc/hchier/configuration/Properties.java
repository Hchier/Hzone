package cc.hchier.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/2/14 13:36
 */
@Component
public class Properties {

    @Value("${properties.redis.hashForToken}")
    public String hashForToken;

    @Value("${properties.redis.tokenLifeCycle}")
    public int tokenLifeCycle;

    @Value("${properties.redis.zsetForTokenExpireTime}")
    public String zsetForTokenExpireTime;

    @Value("${properties.staticResources.path}")
    public String path;

    @Value("${properties.staticResources.avatarPathPrefix}")
    public String avatarPathPrefix;

    @Value("${properties.staticResources.picPathPrefix}")
    public String picPathPrefix;

    @Value("${properties.staticResources.videoPathPrefix}")
    public String videoPathPrefix;

    @Value("${properties.nginx.addr}")
    public String nginxAddr;
}
