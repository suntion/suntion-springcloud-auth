package com.suntion.withhold.service;

import com.suntion.common.lang.ResponseEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankCardFallbackFactory implements FallbackFactory<BankCardFeginClient> {
    @Override
    public BankCardFeginClient create(Throwable cause) {
        return new BankCardFeginClient() {
            @Override
            public ResponseEntity withhold(String idcard, BigDecimal amount) {
                return ResponseEntity.FAILED().setResult("bankcard 服务降级");
            }
        };
    }
}
