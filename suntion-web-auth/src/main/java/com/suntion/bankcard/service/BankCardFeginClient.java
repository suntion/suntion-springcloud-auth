package com.suntion.bankcard.service;

import com.suntion.common.lang.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * @author Suntion
 */
@FeignClient(name = "suntion-bankcard", fallbackFactory = BankCardFallbackFactory.class)
public interface BankCardFeginClient {
    /**
     * 调用代扣接口
     * @param idcard 身份证号
     * @param amount 扣款金额 单位分
     * @return 扣款结果
     */
    @GetMapping("bankcard/api/withhold/{idcard}/{amount}")
    ResponseEntity withhold(@PathVariable("idcard") String idcard, @PathVariable("amount") BigDecimal amount);
}
