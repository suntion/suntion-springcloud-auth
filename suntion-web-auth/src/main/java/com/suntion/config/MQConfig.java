package com.suntion.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }



    @Bean
    public Queue topicMessage1Queue() {
        return new Queue("topic.message.test1");
    }
    @Bean
    public Queue topicMessage2Queue() {
        return new Queue("topic.message.test2");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic.exchange");
    }

    @Bean
    Binding bindTopic1() {
        return BindingBuilder.bind(topicMessage1Queue()).to(topicExchange()).with("message.1");
    }

    @Bean
    Binding bindTopic2() {
        return BindingBuilder.bind(topicMessage2Queue()).to(topicExchange()).with("message.#");
    }



    @Bean
    public Queue fanoutQueue() {
        return new Queue("fanout.queue");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }
    @Bean
    Binding bindFanout() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

}
