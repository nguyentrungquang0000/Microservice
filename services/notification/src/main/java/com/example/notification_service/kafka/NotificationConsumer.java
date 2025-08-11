package com.example.notification_service.kafka;

import com.example.notification_service.kafka.order.OrderConfirmation;
import com.example.notification_service.notification.Notification;
import com.example.notification_service.notification.NotificationRepository;
import com.example.notification_service.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmationNotification(OrderConfirmation orderConfirmation) {
        log.info("OrderConfirmation: {}", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

    }
}
