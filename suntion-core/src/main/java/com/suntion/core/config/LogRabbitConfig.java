package com.suntion.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogRabbitConfig {

    /**
     * 如果要创建只有一个consumer使用的临时queue可以组合使用auto-delete和 exclusive.consumer一旦断开连接该队列自动删除.
     * @return
     */
    @Bean
    public Queue logQueue() {
        return new Queue("logs.queue", false, false, false);
    }
    @Bean
    public TopicExchange logExchange() {
        return new TopicExchange("logs", false, false);
    }
    @Bean
    public Binding logBinding() {
        return BindingBuilder.bind(logQueue()).to(logExchange()).with("logs.queue");
    }
}
