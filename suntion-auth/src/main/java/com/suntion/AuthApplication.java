package com.suntion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableDiscoveryClient
@EnableFeignClients
public class AuthApplication {

    private static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AuthApplication.class, args);
    }

}