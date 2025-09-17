package com.example.review.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewRequest {
    private String content;
    private float rating;
    private String productId;
    private String orderId;
    List<MultipartFile> files;
}
