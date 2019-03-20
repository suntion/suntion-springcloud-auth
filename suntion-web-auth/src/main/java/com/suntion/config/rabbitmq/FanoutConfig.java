package com.suntion.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingA() {
        return BindingBuilder.bind(AMessage()).to(fanoutExchange());
    }

    @Bean
    Binding bindingB() {
        return BindingBuilder.bind(BMessage()).to(fanoutExchange());
    }

    @Bean
    Binding bindingC() {
        return BindingBuilder.bind(CMessage()).to(fanoutExchange());
    }



}
