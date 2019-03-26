package com.suntion.bankcard;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author Suntion
 */
@SpringCloudApplication
@EnableFeignClients
public class BankcardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankcardApplication.class, args);
    }
}
