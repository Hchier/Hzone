package cc.hchier.configuration;

import cc.hchier.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2023/2/14 13:36
 */
@Configuration
public class PropertiesConfig {
    @Bean
    public Properties properties(){
        return new Properties();
    }
}
