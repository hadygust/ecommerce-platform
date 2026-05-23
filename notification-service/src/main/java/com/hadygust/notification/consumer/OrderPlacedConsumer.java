package com.hadygust.notification.consumer;

import com.hadygust.notification.dto.OrderPlacedEvent;
import com.hadygust.notification.service.NotificationService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedConsumer {

    private final NotificationService service;

    @RabbitListener(
            queues = "${notification.queues.order-placed}",
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handleOrderPlaced(OrderPlacedEvent event, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.debug("Received order.placed event: eventId={}", event.getEventId());
        try {
            service.processOrderPlaced(event);
            channel.basicAck(deliveryTag, false);
        } catch (Exception ex){
            log.error("Failed to process order.status.changed event eventId={} — sending to DLQ",
                    event.getEventId(), ex);
            channel.basicNack(deliveryTag, false, false);
        }
    }

}
