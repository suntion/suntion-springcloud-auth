package com.suntion.rabbitmq.controller;


import com.suntion.common.lang.ResponseEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Suntion
 */
@RestController
@RequestMapping("/auth/rabbitmq")
public class RabbitmqTestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test/queue")
    public ResponseEntity sendqueue() {
        String content = "hello";
        System.out.println("Sender:" + content);
        this.rabbitTemplate.convertAndSend("hello", content);
        return ResponseEntity.success();
    }

    @GetMapping("/test/topic1")
    public ResponseEntity sendTopic1() {
        this.rabbitTemplate.convertAndSend("topic.exchange", "message.1", "hi, i am message message1");
        return ResponseEntity.success();
    }

    @GetMapping("/test/topic2")
    public ResponseEntity sendTopic2() {
        this.rabbitTemplate.convertAndSend("topic.exchange", "message.2", "hi, i am message message1");
        return ResponseEntity.success();
    }

    @GetMapping("/test/fanout")
    public ResponseEntity sendfanout() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanout.exchange", "", context);
        return ResponseEntity.success();
    }
}
