package com.suntion.withhold;

import com.suntion.common.lang.ResponseEntity;
import com.suntion.withhold.service.BankCardFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class WithholdController {

    @Autowired
    BankCardFeginClient bankCardFeginClient;

    @GetMapping("/withhold")
    public ResponseEntity withhold() {


        return ResponseEntity.SUCCESS();
    }
}
