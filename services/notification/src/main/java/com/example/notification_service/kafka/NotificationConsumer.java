package com.example.notification_service.kafka;

import com.example.notification_service.kafka.order.OrderConfirmation;
import com.example.notification_service.notification.Notification;
import com.example.notification_service.notification.NotificationRepository;
import com.example.notification_service.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j

public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmationNotification(OrderConfirmation orderConfirmation) {
        log.info("OrderConfirmation: {}", orderConfirmation);
        String title = "Xác nhận đặt hàng";
        String content = "Đơn hàng " + orderConfirmation.getOrderId() +" đã đặt thành công. Hãy thanh toán để sớm nhận được hàng!" ;
        String linkAction = "";
        Notification notification = Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .date(LocalDateTime.now())
                .content(content)
                .title(title)
                .linkAction(linkAction)
                .data(orderConfirmation.getOrderId())
                .userId(orderConfirmation.getAddress().getCustomerId())
                .build();
        try {
            notificationRepository.save(notification);
            messagingTemplate.convertAndSend("/topic/notification/"+orderConfirmation.getAddress().getCustomerId(), notification);
        }catch (MessagingException e) {
            throw new MessagingException(e.getMessage());
        }

    }
}
