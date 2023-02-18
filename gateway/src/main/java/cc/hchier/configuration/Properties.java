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

    @Value("${properties.hashForToken}")
    public String hashForToken;

    @Value("#{${properties.whitePaths}}")
    public Map<String, String> whitePaths;
}
