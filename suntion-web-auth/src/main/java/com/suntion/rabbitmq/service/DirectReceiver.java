package com.suntion.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = "hello")
public class DirectReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("hello Receiver1  : " + message);
    }
}
