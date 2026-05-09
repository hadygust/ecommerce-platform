package com.hadygust.ecommerce.controller;

import com.hadygust.ecommerce.dto.request.CreateProductRequest;
import com.hadygust.ecommerce.dto.response.ProductResponse;
import com.hadygust.ecommerce.exception.ProductNotFoundException;
import com.hadygust.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody CreateProductRequest req){
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search
            ){
        return ResponseEntity.ok(service.findByCategoryAndName(page, size, category, search));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id){
//        ProductResponse resp;
//        try {
//            resp = service.findById(UUID.fromString(id));
//        } catch (ProductNotFoundException e){
//            ResponseEntity.status(404).body(new ErrorRes)
//        }
        return ResponseEntity.ok(service.findById(UUID.fromString(id)));
    }
}
