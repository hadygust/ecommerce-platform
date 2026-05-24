package com.hadygust.notification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
        return QueueBuilder.durable(orderPlacedQueue).build();
    }

    @Bean
    public Queue orderStatusChangedQueue(){
        return QueueBuilder.durable(orderStatusChangedQueue).build();
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

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
