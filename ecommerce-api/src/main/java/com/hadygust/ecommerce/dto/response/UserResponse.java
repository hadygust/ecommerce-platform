package com.hadygust.ecommerce.dto.response;

import jakarta.validation.constraints.Email;

import java.util.UUID;

public record UserResponse(

        UUID id,
        String name,
        @Email
        String email
) {}
