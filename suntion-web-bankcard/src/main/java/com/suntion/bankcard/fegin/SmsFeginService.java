package com.suntion.bankcard.fegin;


import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "suntion-sms", fallbackFactory = SmsFallbackFactory.class)
public interface SmsFeginService {
    @RequestMapping("sms/api/{phone}/{content}")
    public ResponseEntity sms(@PathVariable("phone") String phone, @PathVariable("content") String content);
}
