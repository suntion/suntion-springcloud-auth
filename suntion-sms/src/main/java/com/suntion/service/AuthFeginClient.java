package com.suntion.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "suntion-auth", fallbackFactory = AuthFeginClientFallbackFactory.class)
public interface AuthFeginClient {
    @GetMapping("/auth/quartz/jobs")
    public Object getJobs();
}