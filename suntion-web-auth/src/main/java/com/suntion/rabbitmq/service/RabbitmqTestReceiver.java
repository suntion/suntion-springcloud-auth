package com.suntion.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqTestReceiver {

    @RabbitListener(queues = "hello")
    public void hello(String msg){
        System.err.println("hello:" + msg);
    }


    @RabbitListener(queues = "topic.message.test1")
    public void message1(String msg){
        System.err.println("topic.message.test1:" + msg);
    }

    @RabbitListener(queues = "topic.message.test2")
    public void message2(String msg){
        System.err.println("topic.message.test2:" + msg);
    }


    @RabbitListener(queues = "fanout.queue")
    public void fanout(String msg){
        System.err.println("fanout.queue:" + msg);
    }
}
