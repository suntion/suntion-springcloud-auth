package com.suntion.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.common.lang.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

/**
 * @author suntion
 */
@RestController
@RequestMapping("sms/api/")
public class ApiController {
    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/{phone}/{content}")
    public ResponseEntity sms(@PathVariable String phone, @PathVariable String content) {
        try {
            if (90< Integer.valueOf(content) && Integer.valueOf(content) < 95) {
                return ResponseEntity.failed().setResult("短信发送失败");
            }
            if (content.equals("1")) {
                throw new TimeoutException("连接超时");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.failed().setResult("error");
        }

        logger.info("短信发送成功");
        return ResponseEntity.success().setResult("短信发送成功");
    }

    public ResponseEntity error(@PathVariable String phone, @PathVariable String content) {
        //发起某个网络请求（可能失败）
        System.out.println("sms中发生熔断");
        return ResponseEntity.failed().setResult("sms中发生熔断");
    }
}
