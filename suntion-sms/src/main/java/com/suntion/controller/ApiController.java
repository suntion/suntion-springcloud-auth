package com.suntion.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.common.lang.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("sms/api/")
public class ApiController {

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/{phone}/{content}")
    public ResponseEntity sms(@PathVariable String phone, @PathVariable String content) {
        try {
            int ran = (int) (100 * Math.random() + 4500);
            Thread.sleep(ran);
            if (content.equals("123456")) {
                return ResponseEntity.FAILED().setResult("短信发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.FAILED().setResult("error");
        }
        if (content.equals("33333")) {
            throw  new RuntimeException("粗我u");
        }

        System.out.println("短信发送成功");
        return ResponseEntity.SUCCESS().setResult("短信发送成功");
    }

    public ResponseEntity error(@PathVariable String phone, @PathVariable String content) {
        //发起某个网络请求（可能失败）
        return ResponseEntity.FAILED().setResult("sms中发生熔断");
    }
}