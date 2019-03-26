package com.suntion.bankcard.fegin;

import com.suntion.common.lang.ResponseEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Suntion
 */
@Component
public class SmsFallbackFactory implements FallbackFactory<SmsFeginService> {
    @Override
    public SmsFeginService create(Throwable cause) {
        return new SmsFeginService() {
            @Override
            public ResponseEntity sms(String phone, String content) {
                System.out.println("sms 服务降级");
                return ResponseEntity.failed().setResult("sms 服务降级");
            }
        };
    }
}
