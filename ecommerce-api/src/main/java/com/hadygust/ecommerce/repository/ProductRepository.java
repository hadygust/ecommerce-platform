package com.hadygust.ecommerce.repository;

import com.hadygust.ecommerce.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findProductsByCategoryContainingIgnoreCaseAndNameContainingIgnoreCase(
            String category,
            String name,
            Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT p
        FROM Product p
        WHERE p.id = :id
    """)
    Optional<Product> findByIdForUpdate(UUID id);

    Optional<Product> deleteProductById(UUID id);
}
