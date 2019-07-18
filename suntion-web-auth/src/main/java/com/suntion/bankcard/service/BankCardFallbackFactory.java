package com.suntion.bankcard.service;

import com.suntion.common.lang.ResponseEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
/**
 * @author Suntion
 */
@Component
public class BankCardFallbackFactory implements FallbackFactory<BankCardFeginClient> {
    @Override
    public BankCardFeginClient create(Throwable cause) {
        return new BankCardFeginClient() {
            @Override
            public ResponseEntity withhold(String idcard, BigDecimal amount) {
                return ResponseEntity.failed().setResult("bankcard 服务降级");
            }
        };
    }
}
