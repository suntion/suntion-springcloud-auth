package com.suntion.withhold;

import com.suntion.common.lang.ResponseEntity;
import com.suntion.withhold.service.BankCardFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Suntion
 */
@RestController
@RequestMapping("auth")
public class WithholdController {

    @Autowired
    BankCardFeginClient bankCardFeginClient;

    @GetMapping("/withhold/{idcard}/{amount}")
    public ResponseEntity withhold(@PathVariable String idcard, @PathVariable BigDecimal amount) {
        ResponseEntity responseEntity = bankCardFeginClient.withhold(idcard, amount);
        System.out.println("auth 发起代扣结果" + responseEntity.toString());
        return responseEntity;
    }
}
