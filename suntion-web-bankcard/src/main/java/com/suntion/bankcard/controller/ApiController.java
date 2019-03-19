package com.suntion.bankcard.controller;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.bankcard.service.WithHoldService;
import com.suntion.common.lang.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("bankcard/api")
public class ApiController {

    @Autowired
    WithHoldService withHoldService;

    @GetMapping("withhold/{idcard}/{amount}")
    @HystrixCommand(fallbackMethod = "error")
    public ResponseEntity withhold(@PathVariable String idcard, @PathVariable BigDecimal amount) {
        boolean falg = withHoldService.withHold(idcard, amount);
        if (falg) {
            System.out.println("代扣结果成功");
            return ResponseEntity.SUCCESS();
        }
        System.out.println("代扣结果失败");
        return ResponseEntity.FAILED();
    }
    public ResponseEntity error(@PathVariable String phone, @PathVariable BigDecimal content) {
        //发起某个网络请求（可能失败）
        return ResponseEntity.FAILED().setResult("bankcard中发生熔断");
    }

}
