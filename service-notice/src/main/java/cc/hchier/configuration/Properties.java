package cc.hchier.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author by Hchier
 * @Date 2023/2/14 13:36
 */
@Component
public class Properties {
    @Value("${properties.authCodeLifeCycle}")
    public int authCodeLifeCycle;
}
