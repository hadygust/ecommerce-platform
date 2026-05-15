package com.hadygust.ecommerce.dto.request;

import com.hadygust.ecommerce.entity.enums.OrderStatus;

public record UpdateOrderStatusRequest(
        OrderStatus status
) {}
