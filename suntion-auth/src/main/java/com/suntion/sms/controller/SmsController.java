package com.suntion.sms.controller;

import com.suntion.sms.service.SmsFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {
    @Autowired
    SmsFeginClient smsFeginClient;

    @GetMapping("/unauth/sms/{phone}/{content}")
    public Object miaosha(@PathVariable String phone, @PathVariable String content) {
        return smsFeginClient.sms(phone, content);
    }
}
