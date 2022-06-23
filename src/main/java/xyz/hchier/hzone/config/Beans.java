package xyz.hchier.hzone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.hchier.hzone.Utils.Md5Util;

/**
 * @author by Hchier
 * @Date 2022/6/23 14:37
 */
@Configuration
public class Beans {
    @Bean("ObjectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean("ModelMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}