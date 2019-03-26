package com.suntion.bankcard.fegin;


import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Suntion
 */
@FeignClient(name = "suntion-sms", fallbackFactory = SmsFallbackFactory.class)
public interface SmsFeginService {
    /**
     * 调用发送短信服务
     *
     * @param phone   电话号码
     * @param content 短信内容
     * @return 发送结果
     */
    @RequestMapping("sms/api/{phone}/{content}")
    public ResponseEntity sms(@PathVariable("phone") String phone, @PathVariable("content") String content);
}
