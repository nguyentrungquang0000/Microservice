package com.example.review.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String content;
    private float rating;
    private String userId;
    private String productId;
    private String orderId;
    private String reply;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "review")
    private List<ReviewFile> reviewFiles;
}
