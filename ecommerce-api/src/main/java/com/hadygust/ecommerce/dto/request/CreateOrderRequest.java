package com.hadygust.ecommerce.dto.request;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItemRequest> items
) {}
