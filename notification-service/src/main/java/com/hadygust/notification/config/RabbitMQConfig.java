package com.hadygust.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${notification.exchange}")
    private String exchange;

    @Value("${notification.queues.order-placed}")
    private String orderPlacedQueue;

    @Value("${notification.queues.order-status-changed}")
    private String orderStatusChangedQueue;

    @Value("${notification.routing-keys.order-placed}")
    private String orderPlacedRoutingKey;

    @Value("${notification.routing-keys.order-status-changed}")
    private String orderStatusChangedRoutingKey;


    // Exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }


    // Queue
    @Bean
    public Queue orderPlacedQueue(){
        return new Queue(orderPlacedQueue);
    }

    @Bean
    public Queue orderStatusChangedQueue(){
        return new Queue(orderStatusChangedQueue);
    }

    // Binding
    @Bean
    Binding orderPlacedBinding(){
        return BindingBuilder
                .bind(orderPlacedQueue())
                .to(exchange())
                .with(orderPlacedRoutingKey);
    }


    // Jackson
    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
