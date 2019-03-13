package com.suntion.sms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "suntion-sms", fallbackFactory = SmsFeginClientFallbackFactory.class)
public interface SmsFeginClient {

    @PostMapping("/sms/{phone}/{content}")
    String sms(@PathVariable String phone, @PathVariable String content);

    @GetMapping("/sms/test")
    String smstest();
}
