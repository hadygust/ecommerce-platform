package com.hadygust.notification.repository;

import com.hadygust.notification.entity.ProcessedEvent;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, UUID> {

    @Override
    boolean existsById(@Nonnull UUID uuid);
}
