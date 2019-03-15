package com.suntion.withhold.service;

import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "suntion-bankcard", fallbackFactory = BankCardFallbackFactory.class)
public interface BankCardFeginClient {

}
