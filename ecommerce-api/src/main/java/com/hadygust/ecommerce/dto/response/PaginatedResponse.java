package com.hadygust.ecommerce.dto.response;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> data,

        int page,

        int size,

        long totalElements,

        int totalPages,

        boolean first,

        boolean last
) {
}
