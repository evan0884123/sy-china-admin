package com.sychina.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SyChinaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyChinaAdminApplication.class, args);
    }

}
