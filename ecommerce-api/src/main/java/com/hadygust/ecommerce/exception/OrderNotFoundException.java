package com.hadygust.ecommerce.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super("Order not found for id: " + id
        );
    }
}
