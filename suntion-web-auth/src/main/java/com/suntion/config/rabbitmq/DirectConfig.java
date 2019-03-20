package com.suntion.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    final static String queue_helle = "hello";

    @Bean
    public Queue queueHello() {
        return new Queue(DirectConfig.queue_helle);
    }
}
