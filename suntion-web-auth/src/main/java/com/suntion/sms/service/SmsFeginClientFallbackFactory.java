package com.suntion.sms.service;

import com.suntion.common.lang.ResponseEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Suntion
 */
@Component
public class SmsFeginClientFallbackFactory implements FallbackFactory<SmsFeginClient> {
    @Override
    public SmsFeginClient create(Throwable throwable) {
        return new SmsFeginClient() {
            @Override
            public ResponseEntity sms(String phone, String content) {
                return ResponseEntity.failed("有没有对应的信息,此刻服务已经关闭");
            }
        };
    }
}
