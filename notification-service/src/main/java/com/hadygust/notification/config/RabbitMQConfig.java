package com.hadygust.notification.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private String exchange;

    private String orderPlacedQueue;

    private String orderStatusChangedQueue;

    private String orderPlacedRoutingKey;

    private String orderStatusChangedRoutingKey;

}
