package com.suntion.withhold.service;

import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "suntion-bankcard", fallbackFactory = BankCardFallbackFactory.class)
public interface BankCardFeginClient {
    @GetMapping("bankcard/api/withhold/{idcard}/{amount}")
    public ResponseEntity withhold(@PathVariable("idcard") String idcard, @PathVariable("amount") BigDecimal amount);
}
