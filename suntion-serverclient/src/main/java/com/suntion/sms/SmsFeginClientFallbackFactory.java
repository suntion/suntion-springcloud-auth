package com.suntion.sms;

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
                return ResponseEntity.failed(throwable.getMessage());
            }
        };
    }
}
