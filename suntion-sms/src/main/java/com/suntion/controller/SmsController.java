package com.suntion.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${server.port}")
    private Integer port;

    @PostMapping("/sms/{phone}/{content}")
    public String sms(@PathVariable String phone, @PathVariable String content) {
        return phone+"-" + content + "-----"+port;
    }

    @GetMapping("/sms/test")
    public String sms() {
        return "-----test";
    }
}
