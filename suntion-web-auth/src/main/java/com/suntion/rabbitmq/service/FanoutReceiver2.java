package com.suntion.rabbitmq.service;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = "fanout.B")
public class FanoutReceiver2 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver2  : " + message);
    }
}
