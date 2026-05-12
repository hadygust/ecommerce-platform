package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.CreateOrderRequest;
import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest req){
        return ResponseEntity.ok(service.createOrder(req));
    }
}
