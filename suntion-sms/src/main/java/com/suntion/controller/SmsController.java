package com.suntion.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class SmsController {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("/sms/{phone}/{content}")
    public String sms(@PathVariable String phone, @PathVariable String content) {
        return phone+"-" + content + "-----"+port;
    }

    @GetMapping("/sms/test")
    public String sms() {
        return "-----test" + port;
    }
}
