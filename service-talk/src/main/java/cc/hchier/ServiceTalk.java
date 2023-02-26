package cc.hchier;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hchier
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceTalk {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTalk.class, args);
    }
}