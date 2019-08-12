package com.suntion.sms;

import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



/**
 * @author Suntion
 */
@FeignClient(value = "suntion-sms", fallbackFactory = SmsFeginClientFallbackFactory.class)
public interface SmsFeginClient {

    /**
     * 发送短信
     *
     * @param phone   电话
     * @param content 短信内容
     * @return 发送结果
     */
    @PostMapping("/sms/api/{phone}/{content}")
    ResponseEntity sms(@PathVariable("phone") String phone, @PathVariable("content") String content);
}
