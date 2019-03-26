package com.suntion.sms.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Suntion
 */
@Component
public class SmsFeginClientFallbackFactory implements FallbackFactory<SmsFeginClient> {
    @Override
    public SmsFeginClient create(Throwable throwable) {
        return new SmsFeginClient() {
            @Override
            public String sms(String phone, String content) {
                return "有没有对应的信息,此刻服务已经关闭";
            }

            @Override
            public String smstest() {
                return "有没有对应的信息,此刻服务已经关闭";
            }
        };
    }
}
