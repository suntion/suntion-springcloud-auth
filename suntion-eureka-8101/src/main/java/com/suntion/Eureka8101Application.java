package com.suntion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka8101Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka8101Application.class, args);
    }
}
