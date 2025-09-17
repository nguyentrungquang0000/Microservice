package com.example.notification_service.notification;

import com.example.notification_service.modal.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getNotifications(@RequestParam(defaultValue = "") String lastNotificationId,
                                                           @RequestParam(defaultValue = "5") int limit,
                                                           @RequestHeader("X-User-Id") String userId) {
        return service.getNotifications(lastNotificationId, limit, userId);
    }
}
