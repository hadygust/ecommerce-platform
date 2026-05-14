package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.CreateOrderRequest;
import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.dto.response.PaginatedResponse;
import com.hadygust.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest req){
        return ResponseEntity.ok(service.createOrder(req));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<OrderResponse>> getUserOrders(@RequestParam(defaultValue = "0") Integer page,
                                                                          @RequestParam(defaultValue = "20") Integer size){
        return ResponseEntity.ok(service.getUserOrders(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id){
        return ResponseEntity.ok(service.getOrder(id));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable UUID id){
        return ResponseEntity.ok(service.cancelOrder(id));
    }

}
