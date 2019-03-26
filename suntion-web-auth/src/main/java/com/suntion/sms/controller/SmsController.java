package com.suntion.sms.controller;

import com.suntion.sms.service.SmsFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Suntion
 */
@RestController
@RequestMapping("auth")
public class SmsController {
    @Autowired
    SmsFeginClient smsFeginClient;

    @GetMapping("/fegin/sms/{phone}/{content}")
    public Object miaosha(@PathVariable("phone") String phone, @PathVariable("content") String content) {
        return smsFeginClient.sms(phone, content);
    }

    @GetMapping("/fegin/sms/test")
    public Object test() {
        return smsFeginClient.smstest();
    }
}
