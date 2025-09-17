package com.example.review.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String key;
    private String fileUrl;
    private String type;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
