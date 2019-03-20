package com.suntion.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LogReceiver {

    @RabbitListener(queues = {"log.queue"})
    public void processMessage(String msg) {
        System.err.println("consumer receive a message:" + msg);
    }
}
