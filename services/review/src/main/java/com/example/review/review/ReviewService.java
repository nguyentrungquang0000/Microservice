package com.example.review.review;

import com.example.review.ApiResponse;
import com.example.review.Customer.CustomerClient;
import com.example.review.Customer.CustomerDTO;
import com.example.review.orders.OrderClient;
import com.example.review.orders.OrderDTO;
import com.example.review.orders.OrderStatus;
import com.example.review.review.review_response.ReviewFileResponse;
import com.example.review.review.review_response.ReviewResponse;
import com.example.review.s3.FileResponse;
import com.example.review.s3.S3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final S3Client s3Client;
    private final OrderClient orderClient;
    private final CustomerClient customerClient;
    @Transactional
    public ResponseEntity<?> addReview(List<ReviewRequest> requestList, String userId) {
        ApiResponse<OrderDTO> apiResponse = orderClient.getOrderById(requestList.getFirst().getOrderId());

//        if (!LocalDateTime.now().isBefore(apiResponse.getData().getCreatedAt().plusDays(7)) || !apiResponse.getData().getStatus().equals(OrderStatus.DELIVERED)) {
//            // Xử lý trong 7 ngày kể từ createdAt
//            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Hết hạn đánh giá", null));
//        }
        for(ReviewRequest request : requestList){
            Review review = Review.builder()
                    .content(request.getContent())
                    .rating(request.getRating())
                    .userId(userId)
                    .productId(request.getProductId())
                    .orderId(request.getOrderId())
                    .createdAt(LocalDateTime.now())
                    .build();
            review = reviewRepository.save(review);

            for (MultipartFile file : request.files){
                String key = "review" + "*" + review.getId();
                FileResponse fileResponse = s3Client.uploadFile(file, key);
                ReviewFile reviewFile = new ReviewFile();
                reviewFile.setFileUrl(fileResponse.getFileUrl());
                reviewFile.setKey(fileResponse.getKey());
                reviewFile.setType(fileResponse.getType());
                reviewFile.setReview(review);
                reviewFileRepository.save(reviewFile);
            }
        }

        Map<String, String> statusRequest = new HashMap<>();
        statusRequest.put("orderId", requestList.getFirst().getOrderId());
        statusRequest.put("status", String.valueOf(OrderStatus.SUCCESS));
        orderClient.putStatusOrder(statusRequest);

        return ResponseEntity.ok("Review successfully added");
    }

    public ResponseEntity<?> getReview(String productId, LocalDateTime dateLast) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(0, 5, sort);
        Page<Review> reviewPage = reviewRepository.getReviewsPage(productId, dateLast, pageable);
        if (dateLast != null) {
            dateLast = dateLast.minusNanos(1);
        }
        
        List<ReviewResponse> reviewList = new ArrayList<>();
        for (Review review : reviewPage.getContent()) {
            List<ReviewFileResponse> files = review.getReviewFiles().stream().map(
                    reviewFile -> ReviewFileResponse.builder()
                            .id(reviewFile.getId())
                            .fileUrl(reviewFile.getFileUrl())
                            .build()
            ).toList();
            CustomerDTO customerDTO = customerClient.getCustomerId(review.getUserId());
            ReviewResponse reviewResponse = ReviewResponse.builder()
                    .id(review.getId())
                    .content(review.getContent())
                    .rating(review.getRating())
                    .reply(review.getReply())
                    .customer(customerDTO)
                    .createdAt(review.getCreatedAt())
                    .files(files)
                    .build();
            reviewList.add(reviewResponse);
        }
        return ResponseEntity.ok(reviewList);
    }

    public ResponseEntity<?> replyReview(String reviewId, String reply) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setReply(reply);
        reviewRepository.save(review);
        return ResponseEntity.ok("ok");
    }

    public int getReviewCount(String productId) {
        List<Review> review = reviewRepository.findByProductIdAndReplyIsNullOrEmpty(productId);
        return review.size();
    }
}
