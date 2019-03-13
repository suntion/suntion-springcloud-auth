package com.suntion.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthFeginClientFallbackFactory implements FallbackFactory<AuthFeginClient> {
    @Override
    public AuthFeginClient create(Throwable cause) {
        return new AuthFeginClient() {
            @Override
            public Object getJobs() {
                return "有没有对应的信息,此刻服务已经关闭";
            }
        };
    }
}
