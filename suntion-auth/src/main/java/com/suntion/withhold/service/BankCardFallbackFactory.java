package com.suntion.withhold.service;

import feign.hystrix.FallbackFactory;

public class BankCardFallbackFactory implements FallbackFactory<BankCardFeginClient> {
    @Override
    public BankCardFeginClient create(Throwable cause) {
        return null;
    }
}
