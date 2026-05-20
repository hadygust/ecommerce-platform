package com.hadygust.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    private String exchange = "ecommerce.events";

    private String orderPlacedQueue = "notification.order.placed";

    private String orderStatusChangedQueue = "notification.order.status.changed";

    private String orderPlacedRoutingKey = "order.placed";

    private String orderStatusChangedRoutingKey = "order.status.changed";



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
