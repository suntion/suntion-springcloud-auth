package com.suntion.bankcard.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Suntion
 */
@Service
public class WithHoldServiceImpl implements WithHoldService {

    @Override
    public Boolean withHold(String idCard, BigDecimal amount) {
        if(amount.doubleValue() > 5) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
