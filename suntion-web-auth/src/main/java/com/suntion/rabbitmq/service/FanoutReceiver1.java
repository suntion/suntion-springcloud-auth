package com.suntion.rabbitmq.service;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = "fanout.A")
public class FanoutReceiver1 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver1  : " + message);
    }
}
