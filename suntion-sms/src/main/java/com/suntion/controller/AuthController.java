package com.suntion.controller;

import com.suntion.service.AuthFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthFeginClient authFeginClient;


    @GetMapping("/sms/jobs")
    public Object jobs() {
        return authFeginClient.getJobs();
    }

    @GetMapping("/sms/jobs2")
    public Object jobs2() {
        return authFeginClient.getJobs();
    }
}
