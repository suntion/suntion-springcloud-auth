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

    @GetMapping("/sendmq/queue")
    public ResponseEntity sendqueue() {
        String content = "hello";
        System.out.println("Sender:" +content);
        this.amqpTemplate.convertAndSend("hello",content);
        return ResponseEntity.SUCCESS();
    }

    @GetMapping("/sendmq/topic")
    public ResponseEntity sendTopic() {
        this.amqpTemplate.convertAndSend("exchange", "topic.message", "hi, i am message topic.message");
        this.amqpTemplate.convertAndSend("exchange", "topic.all_message", "hi, i am message topic.all_message");
        this.amqpTemplate.convertAndSend("exchange", "topic.asdasd", "hi, i am message topic.asdasd");
        return ResponseEntity.SUCCESS();
    }

    @GetMapping("/sendmq/fanout")
    public ResponseEntity sendfanout() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.amqpTemplate.convertAndSend("fanoutExchange","", context);
        return ResponseEntity.SUCCESS();
    }
}
