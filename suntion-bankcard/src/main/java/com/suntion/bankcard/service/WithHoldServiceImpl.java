package com.suntion.bankcard.service;

import com.suntion.bankcard.fegin.SmsFeginService;
import com.suntion.common.constants.HttpConstants;
import com.suntion.common.lang.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithHoldServiceImpl implements WithHoldService{

    @Autowired
    SmsFeginService smsFeginService;

    @Override
    public Boolean withHold(String idCard, BigDecimal amount) {
        ResponseEntity responseEntity = smsFeginService.sms(idCard, amount.toString());
        if (HttpConstants.CODE_SUCCESS.equals(responseEntity.getCode())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
