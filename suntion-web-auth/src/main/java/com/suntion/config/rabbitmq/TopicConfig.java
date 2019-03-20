package com.suntion.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    final static String topic_message = "topic.message";
    final static String topic_all_messages = "topic.all_messages";


    @Bean
    public Queue queueMessage() {
        return new Queue(TopicConfig.topic_message);
    }

    @Bean
    public Queue queueAllMessage() {
        return new Queue(TopicConfig.topic_all_messages);
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(queueMessage()).to(exchange()).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages() {
        return BindingBuilder.bind(queueAllMessage()).to(exchange()).with("topic.#");
    }
}
