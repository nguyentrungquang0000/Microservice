package com.example.notification_service.notification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    @Query(value = """
    {
        userId: ?0,
        $or: [
            { date: { $lt: ?1 } },
            { date: ?1, _id: { $lt: ObjectId(?2) } }
        ]
    }
    """, sort = "{ date: -1, _id: -1 }")
        List<Notification> getNotificationOld(
                String userId,
                LocalDateTime beforeDate,
                String beforeId,
                Pageable pageable
        );

    List<Notification> getByUserId(String userId, Pageable pageable);
}
