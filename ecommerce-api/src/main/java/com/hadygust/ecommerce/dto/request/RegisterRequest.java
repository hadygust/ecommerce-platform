package com.hadygust.ecommerce.dto.request;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
        @Email
        String email,
        String name,
        String password
) {}
