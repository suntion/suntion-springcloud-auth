package com.suntion.sms.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class HelloListener {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("hello  : " + hello);
    }

}