package com.suntion.auth.controller;

import com.suntion.core.common.lang.ResponseEntity;
import com.suntion.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class SmsController {
    @Autowired
    SmsService smsService;

    @GetMapping("/unauth/sms/{phone}/{content}")
    public Object miaosha(@PathVariable String phone, @PathVariable String content) {
        return smsService.sms(phone, content);
    }
}
