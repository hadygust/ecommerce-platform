package com.hadygust.ecommerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;

public record CreateProductRequest(
   String name,
   String description,
   BigDecimal price,
   Integer stock,
   String category
) {}
