package com.suntion.controller;

import com.suntion.service.AuthFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class AuthController {

    @Autowired
    AuthFeginClient authFeginClient;


    @GetMapping("/jobs")
    public Object jobs() {
        return authFeginClient.getJobs();
    }
}
