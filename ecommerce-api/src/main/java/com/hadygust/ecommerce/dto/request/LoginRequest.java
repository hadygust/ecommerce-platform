package com.hadygust.ecommerce.dto.request;

import jakarta.validation.constraints.Email;

public record LoginRequest(
        @Email
        String email,
        String password
) {
}
