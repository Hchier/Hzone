package cc.hchier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hchier
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceNotice {
    public static void main(String[] args) {
        SpringApplication.run(ServiceNotice.class, args);
    }
}