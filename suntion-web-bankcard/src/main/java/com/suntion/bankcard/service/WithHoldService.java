package com.suntion.bankcard.service;

import java.math.BigDecimal;

/**
 * @author Suntion
 */
public interface WithHoldService {

    /**
     * 代扣
     *
     * @param idCard 银行卡
     * @param amount 金额
     * @return 是否代扣成功
     */
    public Boolean withHold(String idCard, BigDecimal amount);
}
