package com.example.notification_service.notification;

import com.example.notification_service.modal.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public ResponseEntity<ApiResponse<?>> getNotifications(String lastNotificationId, int limit, String userId) {
        List<Notification> notificationList = new ArrayList<>();
        if(lastNotificationId == null || lastNotificationId.isEmpty()) {
            Pageable pageable1 = PageRequest.of(0, limit, Sort.by("date").descending());
            notificationList = notificationRepository.getByUserId(userId, pageable1);
        }else{
            Pageable pageable2 = PageRequest.of(0, limit);
            Notification notification = notificationRepository.findById(lastNotificationId).orElse(null);
            if(notification == null) {
                return ResponseEntity.status(500).body(new ApiResponse<>(500, "lastNotification not found", null));
            }
            LocalDateTime lastTime = notification.getDate();
            String lastId = notification.getId();
            notificationList = notificationRepository.getNotificationOld(userId, lastTime, lastId, pageable2);
        }

        return ResponseEntity.ok(new ApiResponse<>(200, "OK", notificationList));
    }
}
