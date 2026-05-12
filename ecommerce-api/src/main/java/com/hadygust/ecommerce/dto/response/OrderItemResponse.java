package com.hadygust.ecommerce.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        ProductResponse product,
        Integer quantity,
        BigDecimal unitPrice
) {
}
