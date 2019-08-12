package com.suntion.sms.controller;

import com.suntion.common.lang.ResponseEntity;
import com.suntion.sms.SmsFeginClient;
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
    public ResponseEntity sms(@PathVariable("phone") String phone, @PathVariable("content") String content) {
        ResponseEntity entity = ResponseEntity.success();
        try {
            entity = smsFeginClient.sms(phone, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
