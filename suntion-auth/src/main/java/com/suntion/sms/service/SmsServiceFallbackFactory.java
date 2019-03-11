package com.suntion.sms.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsServiceFallbackFactory implements FallbackFactory<SmsService> {
    @Override
    public SmsService create(Throwable throwable) {
        return new SmsService() {
            @Override
            public String sms(String phone, String content) {
                return "有没有对应的信息,此刻服务已经关闭";
            }
        };
    }
}
