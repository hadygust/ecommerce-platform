package com.hadygust.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hadygust.notification.dto.OrderPlacedEvent;
import com.hadygust.notification.entity.Notification;
import com.hadygust.notification.entity.ProcessedEvent;
import com.hadygust.notification.repository.NotificationRepository;
import com.hadygust.notification.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;
    private final ProcessedEventRepository processedEventRepo;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processOrderPlaced(OrderPlacedEvent event){
        UUID eventId = event.getEventId();
        if(isDuplicate(eventId)){
            log.warn("Duplicate eventId {} — skipping ORDER_PLACED", eventId);
            return;
        }

        Notification notification = createPendingNotification(
                event.getUserId(),
                event.getUserEmail(),
                Notification.EventType.valueOf(event.getEventType()),
                toJson(event));

        log.info(notification.toString());

        try {
            emailService.sendEmail(notification);
            markNotifSent(notification);
        } catch (Exception ex) {
            markNotifFailed(notification, ex);
            log.error("Failed to send ORDER_PLACED notification. notificationId={} error={}",
                    notification.getId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            markEventProcessed(eventId);
        }

    }

    public Boolean isDuplicate(UUID eventId){
        return eventId != null && processedEventRepo.existsByEventId(eventId);
    }

    public Notification createPendingNotification(UUID userId, String userEmail, Notification.EventType eventType, String payload){
        Notification notification = Notification.builder()
                .userId(userId)
                .userEmail(userEmail)
                .eventType(eventType)
                .payload(payload)
                .status(Notification.EventStatus.PENDING)
                .build();

        return repo.save(notification);
    }

    private void markNotifSent(Notification notification){
        notification.setStatus(Notification.EventStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        repo.save(notification);
    }

    private void markNotifFailed(Notification notification, Exception ex){
        notification.setStatus(Notification.EventStatus.FAILED);
        notification.setRetryCount(notification.getRetryCount() + 1);
        notification.setErrorMessage(truncate(ex.getMessage(), 500));
        repo.save(notification);
    }

    private void markEventProcessed(UUID eventId){
        ProcessedEvent processedEvent = new ProcessedEvent();
        processedEvent.setEventId(eventId);
        processedEventRepo.save(processedEvent);
    }

    private String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String truncate(String s, int maxLength) {
        if (s == null) return null;
        return s.length() <= maxLength ? s : s.substring(0, maxLength);
    }

}
