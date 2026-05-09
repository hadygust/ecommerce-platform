package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.request.CreateProductRequest;
import com.hadygust.ecommerce.dto.response.ProductResponse;
import com.hadygust.ecommerce.entity.Product;
import com.hadygust.ecommerce.exception.ProductNotFoundException;
import com.hadygust.ecommerce.mapper.ProductMapper;
import com.hadygust.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ProductResponse create(CreateProductRequest req){
        Product product = mapper.toEntity(req);
        Product saved = repo.save(product);
        return mapper.toResponse(saved);
    }

    public List<ProductResponse> getAll(){
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponse findById(UUID id){
        Product product = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toResponse(product);
    }

    public List<ProductResponse> findByCategoryAndName(Integer page, Integer size, String category, String name){

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        String categoryFilter = category == null ? "" : category;
        String nameFilter = name == null ? "" : name;

        Page<Product> products = repo.findProductsByCategoryContainingIgnoreCaseAndNameContainingIgnoreCase(categoryFilter, nameFilter, pageable);
        return products.stream().map(mapper::toResponse).toList();
    }

}
