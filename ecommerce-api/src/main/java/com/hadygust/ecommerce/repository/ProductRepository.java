package com.hadygust.ecommerce.repository;

import com.hadygust.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findProductByCategory(String category, Pageable pageable);
    Page<Product> findProductsByNameContainingIgnoreCase(String name, Pageable pageable);
}
