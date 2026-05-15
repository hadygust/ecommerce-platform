package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.dto.response.PaginatedResponse;
import com.hadygust.ecommerce.entity.enums.OrderStatus;
import com.hadygust.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService service;

    @GetMapping
    public ResponseEntity<PaginatedResponse<OrderResponse>> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                                                         @RequestParam(defaultValue = "20") Integer size){
        return ResponseEntity.ok(service.getAllOrders(page, size));
    }

    @PutMapping("/{id}/status")
    public  ResponseEntity<OrderStatus> getOrderStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusRequest status){
        System.out.println(status);
        return ResponseEntity.ok(service.setOrderStatus(id, status.status()));
    }
}
