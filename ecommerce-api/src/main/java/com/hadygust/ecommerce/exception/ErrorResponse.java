package com.hadygust.ecommerce.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        ErrorCode error,
        String message,
        LocalDateTime timestamp
) {}