package com.hadygust.ecommerce.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// OrderPlacedEvent.java
public record OrderPlacedEvent(
        UUID eventId,
        String eventType,
        LocalDateTime timestamp,
        UUID orderId,
        UUID userId,
        String userEmail,
        String userName,
        BigDecimal totalAmount,
        List<OrderItem> items
) {
    public record OrderItem(String productName, Integer quantity, BigDecimal unitPrice) {}
}
