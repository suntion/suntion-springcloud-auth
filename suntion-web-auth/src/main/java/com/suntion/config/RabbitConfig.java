package com.suntion.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private final static String LOG_QUEUE = "log.queue";
    private final static String LOG_EXCHANGEE = "log.exchange";

    @Bean
    public Queue logDirectQueue() {
        return new Queue(this.LOG_QUEUE);
    }
    @Bean
    public DirectExchange logDirectExchange() {
        return new DirectExchange(this.LOG_EXCHANGEE);
    }
    /**
     * 根据路由键绑定队列到交换器上
     * @return
     */
    @Bean
    public Binding logDirectBinding() {
        return BindingBuilder.bind(logDirectQueue()).to(logDirectExchange()).with(this.LOG_QUEUE);
    }
}
