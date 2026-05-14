package com.hadygust.ecommerce.mapper;

import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final OrderItemMapper itemMapper;

    public OrderResponse toResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getTotal(),
                order.getItems().stream().map(itemMapper::toResponse).toList(),
                order.getCreatedAt()
        );
    }

}
