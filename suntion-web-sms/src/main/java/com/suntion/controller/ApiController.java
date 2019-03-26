package com.suntion.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.common.lang.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author suntion
 */
@RestController
@RequestMapping("sms/api/")
public class ApiController {

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/{phone}/{content}")
    public ResponseEntity sms(@PathVariable String phone, @PathVariable String content) {
        try {
            int ran = (int) (100 * Math.random() + 4500);
            Thread.sleep(ran);
            if ("123456".equals(content)) {
                return ResponseEntity.failed().setResult("短信发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.failed().setResult("error");
        }
        if ("33333".equals(content)) {
            throw  new RuntimeException("粗我u");
        }

        System.out.println("短信发送成功");
        return ResponseEntity.success().setResult("短信发送成功");
    }

    public ResponseEntity error(@PathVariable String phone, @PathVariable String content) {
        //发起某个网络请求（可能失败）
        System.out.println("sms中发生熔断");
        return ResponseEntity.failed().setResult("sms中发生熔断");
    }
}