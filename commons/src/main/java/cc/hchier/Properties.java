package cc.hchier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/2/12 17:19
 */
public class Properties {

    @Value("${properties.hashForToken}")
    public String hashForToken;

    @Value("${properties.tokenLifeCycle}")
    public int tokenLifeCycle;

    @Value("${properties.zsetForTokenExpireTime}")
    public String zsetForTokenExpireTime;

    @Value("${properties.authCodeLifeCycle}")
    public int authCodeLifeCycle;

    @Value("#{${properties.whitePaths}}")
    public Map<String, String> whitePaths;
}
