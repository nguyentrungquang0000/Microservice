package com.example.notification_service.notification;

import com.example.notification_service.kafka.order.OrderConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Notification {
    @Id
    private String id;
    private String title;
    private NotificationType type;
    private LocalDateTime date;
    private OrderConfirmation orderConfirmation;
}
