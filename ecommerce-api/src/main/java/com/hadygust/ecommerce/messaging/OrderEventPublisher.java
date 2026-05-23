package com.hadygust.ecommerce.messaging;

import com.hadygust.ecommerce.dto.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${messaging.exchange}")
    private String exchange;

    public void publishOrderPlaced(OrderPlacedEvent event){

        rabbitTemplate.convertAndSend(exchange, "order.placed", event);
    }

}
