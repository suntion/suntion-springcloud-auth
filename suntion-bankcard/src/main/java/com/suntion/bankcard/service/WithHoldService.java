package com.suntion.bankcard.service;

import java.math.BigDecimal;

public interface WithHoldService {

    public Boolean withHold(String idCard, BigDecimal amount);
}
