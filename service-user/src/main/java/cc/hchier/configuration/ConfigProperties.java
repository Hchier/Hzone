package cc.hchier.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author by Hchier
 * @Date 2023/2/12 17:19
 */
@Component
public class ConfigProperties {

    @Value("${configProperties.hashForToken}")
    public String hashForToken;

    @Value("${configProperties.tokenLifeCycle}")
    public int tokenLifeCycle;

    @Value("${configProperties.zsetForTokenExpireTime}")
    public String zsetForTokenExpireTime;

    @Value("${configProperties.authCodeLifeCycle}")
    public int authCodeLifeCycle;
}
