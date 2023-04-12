package cc.hchier.configuration;

import cn.hutool.Hutool;
import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by Hchier
 * @Date 2023/4/12 11:41
 */
@Configuration
public class Beans {
    @Bean
    public Snowflake snowflake(){
        return new Snowflake();
    }
}
