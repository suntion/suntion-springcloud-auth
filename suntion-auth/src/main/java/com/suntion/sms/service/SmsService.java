package com.suntion.sms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "suntion-sms", fallbackFactory = SmsServiceFallbackFactory.class)
public interface SmsService {

    @PostMapping("/sms/{phone}/{content}")
    String sms(@PathVariable String phone, @PathVariable String content);
}
