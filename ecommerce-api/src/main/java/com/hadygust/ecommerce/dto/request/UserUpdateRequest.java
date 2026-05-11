package com.hadygust.ecommerce.dto.request;

import org.springframework.stereotype.Component;

public record UserUpdateRequest(
        String email,
        String name
) {
}
