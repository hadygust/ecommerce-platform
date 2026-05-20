package com.hadygust.notification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPlacedEvent {

    private UUID eventId;
    private String eventType;
    private Instant timestamp;
    private UUID orderId;
    private UUID userId;
    private String userEmail;
    private String userName;
    private BigDecimal totalAmount;
    private List<OrderItem> items;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderItem {
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}