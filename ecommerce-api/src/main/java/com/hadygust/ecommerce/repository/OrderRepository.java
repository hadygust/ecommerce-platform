package com.hadygust.ecommerce.repository;

import com.hadygust.ecommerce.entity.Order;
import com.hadygust.ecommerce.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);

    @Query("""
        SELECT o.id
        FROM Order o
        WHERE o.user.id = :userId
        ORDER BY o.createdAt DESC
    """)
    Page<UUID> findOrderIdByUserId (@Param("userId") UUID userId, Pageable pageable);

    @Query("""
        SELECT o.id
        FROM Order o
        ORDER BY o.createdAt DESC
    """)
    Page<UUID> findAllOrderId (Pageable pageable);

    @Query("""
        SELECT DISTINCT o
        FROM Order o
        LEFT JOIN FETCH o.items i
        LEFT JOIN FETCH i.product
        WHERE o.id IN :ids
        ORDER BY o.createdAt DESC
    """)
    List<Order> findOrdersWithItems (@Param("ids") List<UUID> ids);

    @Query("""
        SELECT o.status
        FROM Order o
        WHERE o.id = :id
    """)
    Optional<OrderStatus> findOrderStatus (@Param("id") UUID id);
}
