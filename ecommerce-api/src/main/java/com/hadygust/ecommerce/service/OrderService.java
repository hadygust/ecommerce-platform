package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.CreateOrderRequest;
import com.hadygust.ecommerce.dto.request.OrderItemRequest;
import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.entity.Order;
import com.hadygust.ecommerce.entity.OrderItem;
import com.hadygust.ecommerce.entity.Product;
import com.hadygust.ecommerce.exception.ProductNotFoundException;
import com.hadygust.ecommerce.helper.UserUtils;
import com.hadygust.ecommerce.mapper.OrderItemMapper;
import com.hadygust.ecommerce.mapper.OrderMapper;
import com.hadygust.ecommerce.repository.OrderItemRepository;
import com.hadygust.ecommerce.repository.OrderRepository;
import com.hadygust.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OrderItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final OrderMapper mapper;
    private final OrderItemMapper itemMapper;
    private final UserUtils userUtils;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req){
        Order order = new Order();
        order.setUser(userUtils.getUser());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemReq : req.items()){
            // Find related Product
            Product product = productRepo.findById(itemReq.id()).orElseThrow(() -> new ProductNotFoundException(itemReq.id()));
            OrderItem item = new OrderItem();
            item.setQuantity(itemReq.quantity());
            item.setOrder(order);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());

            // Count total price fo Order
            total = total.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            items.add(item);
        }
        order.setItems(items);
        order.setTotal(total);

        Order saved = repo.save(order);

        return mapper.toResponse(saved);
    }

}
