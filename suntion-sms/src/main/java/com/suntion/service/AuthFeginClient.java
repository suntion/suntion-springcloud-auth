package com.suntion.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "suntion-auth")
public interface AuthFeginClient {
    @GetMapping("/api/quartz/jobs")
    public Object getJobs();
}