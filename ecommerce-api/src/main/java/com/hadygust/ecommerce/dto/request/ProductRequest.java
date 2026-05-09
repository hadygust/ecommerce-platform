package com.hadygust.ecommerce.dto.request;

import java.math.BigDecimal;

public record ProductRequest(
   String name,
   String description,
   BigDecimal price,
   Integer stock,
   String category
) {}
