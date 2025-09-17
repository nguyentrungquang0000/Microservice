package com.example.notification_service.notification;

import com.example.notification_service.kafka.order.OrderConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
@CompoundIndex(name = "user_date_id_idx", def = "{'userId': 1, 'date': -1, 'id': -1}", background = true)
public class Notification {
    @Id
    private String id;
    private String title;
    private NotificationType type;
    private LocalDateTime date;
    private String content;
    private String data;
    private String linkAction;
    private String userId;
}
