package com.hadygust.ecommerce.service;

import com.hadygust.ecommerce.dto.event.OrderPlacedEvent;
import com.hadygust.ecommerce.dto.request.CreateOrderRequest;
import com.hadygust.ecommerce.dto.request.OrderItemRequest;
import com.hadygust.ecommerce.dto.response.OrderResponse;
import com.hadygust.ecommerce.dto.response.PaginatedResponse;
import com.hadygust.ecommerce.entity.Order;
import com.hadygust.ecommerce.entity.OrderItem;
import com.hadygust.ecommerce.entity.Product;
import com.hadygust.ecommerce.entity.User;
import com.hadygust.ecommerce.entity.enums.OrderStatus;
import com.hadygust.ecommerce.entity.enums.UserRole;
import com.hadygust.ecommerce.exception.OrderNotFoundException;
import com.hadygust.ecommerce.exception.ProductNotFoundException;
import com.hadygust.ecommerce.helper.UserUtils;
import com.hadygust.ecommerce.mapper.OrderMapper;
import com.hadygust.ecommerce.messaging.OrderEventPublisher;
import com.hadygust.ecommerce.repository.OrderRepository;
import com.hadygust.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final ProductRepository productRepo;
    private final OrderMapper mapper;
    private final UserUtils userUtils;
    private final OrderEventPublisher orderEventPublisher;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req){
        Order order = new Order();
        User user = userUtils.getUser();
        order.setUser(user);

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest itemReq : req.items()){
            // Find related Product, Validation, & Stock handling
            Product product = productRepo.findByIdForUpdate(itemReq.id()).orElseThrow(() -> new ProductNotFoundException(itemReq.id()));
            if (product.getStock() < itemReq.quantity()){
                throw new IllegalStateException("Insufficient stock");
            }
            product.setStock(product.getStock() - itemReq.quantity());

            // Item Creation
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

        List<OrderPlacedEvent.OrderItem> eventItems = items
                .stream()
                .map(
                        item -> new OrderPlacedEvent.OrderItem(item.getProduct().getName(), item.getQuantity(), item.getUnitPrice())
                ).toList();

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        orderEventPublisher.publishOrderPlaced(new OrderPlacedEvent(
                                UUID.randomUUID(),
                                "ORDER_PLACED",
                                LocalDateTime.now(),
                                saved.getId(),
                                user.getId(),
                                user.getEmail(),
                                user.getName(),
                                saved.getTotal(),
                                eventItems
                        ));
                    }
                }
        );


        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<OrderResponse> getUserOrders (Integer page, Integer size){
        User user = userUtils.getUser();

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<UUID> orderIds = repo.findOrderIdByUserId(user.getId(), pageable);

        for(UUID id : orderIds){
            System.out.println("id: " +id);
        }

        List<Order> orders = repo.findOrdersWithItems(orderIds.toList());
        System.out.println(orders.size());

        return new PaginatedResponse<>(
                orders.stream().map(mapper::toResponse).toList(),
                orderIds.getPageable().getPageNumber(),
                orderIds.getPageable().getPageSize(),
                orderIds.getTotalElements(),
                orderIds.getTotalPages(),
                orderIds.isFirst(),
                orderIds.isLast()
        );
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(UUID id){
        Order order = validateOwnerAdmin(id);
        return mapper.toResponse(order);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID id) {
        Order order = validateOwnerAdmin(id);

        if (order.getStatus() != OrderStatus.PENDING){
            throw new IllegalStateException("Order cannot be cancelled");
        }

        for (OrderItem item : order.getItems()){
            Product product = productRepo.findByIdForUpdate(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(item.getProduct().getId()));

            product.setStock(product.getStock() + item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);

        return mapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<OrderResponse> getAllOrders(Integer page, Integer size){

        User user = userUtils.getUser();

        if(!user.getRole().equals(UserRole.ADMIN)){
            throw new AccessDeniedException("You are not authorized to do this");
        }

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<UUID> orderIds = repo.findAllOrderId(pageable);

        List<Order> orders = repo.findOrdersWithItems(orderIds.toList());

        return new PaginatedResponse<>(
                orders.stream().map(mapper::toResponse).toList(),
                orderIds.getPageable().getPageNumber(),
                orderIds.getPageable().getPageSize(),
                orderIds.getTotalElements(),
                orderIds.getTotalPages(),
                orderIds.isFirst(),
                orderIds.isLast()
        );
    }

    private Order validateOwnerAdmin(UUID id) {
        User user = userUtils.getUser();

        Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        boolean isOwner = user.getId().equals(order.getUser().getId());
        boolean isAdmin = user.getRole().equals(UserRole.ADMIN);
        if (!isOwner && !isAdmin){
            throw new AccessDeniedException("You are not authorized to view this order");
        }

        return order;
    }

    @Transactional
    public OrderStatus setOrderStatus(UUID id, OrderStatus status) {
        User user = userUtils.getUser();

        if(!user.getRole().equals(UserRole.ADMIN)){
            throw new AccessDeniedException("You are not authorized to do this");
        }

        Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);

        return order.getStatus();
    }
}
