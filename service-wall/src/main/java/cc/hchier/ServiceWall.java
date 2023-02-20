package cc.hchier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hchier
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceWall {
    public static void main(String[] args) {
        SpringApplication.run(ServiceWall.class, args);
    }
}