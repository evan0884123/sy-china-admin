package com.sychina.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
public class SyChinaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyChinaAdminApplication.class, args);
    }

}
