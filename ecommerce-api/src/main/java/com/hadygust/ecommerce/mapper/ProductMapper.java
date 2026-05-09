package com.hadygust.ecommerce.mapper;

import com.hadygust.ecommerce.dto.request.CreateProductRequest;
import com.hadygust.ecommerce.dto.response.ProductResponse;
import com.hadygust.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(CreateProductRequest req) {
        Product p = new Product();
        p.setName(req.name());
        p.setDescription(req.description());
        p.setPrice(req.price());
        p.setStock(req.stock());
        p.setCategory(req.category());
        return p;
    }

    public ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getCategory()
        );
    }

}
