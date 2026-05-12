package com.hadygust.ecommerce.mapper;


import com.hadygust.ecommerce.dto.request.OrderItemRequest;
import com.hadygust.ecommerce.dto.response.OrderItemResponse;
import com.hadygust.ecommerce.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper {

    private final ProductMapper productMapper;

    public OrderItemResponse toResponse(OrderItem item){
        return new OrderItemResponse(
                item.getId(),
                productMapper.toResponse(item.getProduct()),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }

}
