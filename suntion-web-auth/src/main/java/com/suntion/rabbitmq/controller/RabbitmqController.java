package com.suntion.rabbitmq.controller;


import com.suntion.common.lang.ResponseEntity;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/auth/rabbitmq")
public class RabbitmqController {


    @Autowired
    AmqpTemplate amqpTemplate;

    @GetMapping("/sendmq")
    public ResponseEntity sendMq() {
        String content = "hello" + new Date();
        System.out.println("Sender:" +content);
        this.amqpTemplate.convertAndSend("hello",content);
        return ResponseEntity.SUCCESS();
    }
}
