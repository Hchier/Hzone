package xyz.hchier.hzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hchier
 */
@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class HzoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzoneApplication.class, args);
    }

}
