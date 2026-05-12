package com.hadygust.ecommerce.dto.request;

import java.util.UUID;

public record OrderItemRequest(

        UUID id,
        Integer quantity

) {}
