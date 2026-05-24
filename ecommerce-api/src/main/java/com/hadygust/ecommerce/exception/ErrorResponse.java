package com.hadygust.ecommerce.exception;

import java.time.Instant;
import java.time.LocalDateTime;

public record ErrorResponse(
        ErrorCode error,
        String message,
        Instant timestamp
) {}