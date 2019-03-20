package com.suntion.sms.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class HelloListener {

    @RabbitListener(queues = {"logDirectQueue"})
    public void processMessage(String msg) {
        System.err.println("consumer HelloListener a message:" + msg);
    }

}