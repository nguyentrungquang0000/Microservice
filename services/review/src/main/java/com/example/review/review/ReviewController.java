package com.example.review.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(@ModelAttribute ReviewRequestWrapper wrapper,
                                       @RequestHeader("X-User-Id") String userId) {
        return reviewService.addReview(wrapper.getRequest(), userId);
    }


    @GetMapping("/{product-id}")
    public ResponseEntity<?> getReview(@PathVariable("product-id") String productId, @RequestParam(required = false) LocalDateTime dateLast ) {
        return reviewService.getReview(productId, dateLast);
    }

    @PutMapping()
    public ResponseEntity<?> replyReview(@RequestBody Map<String, String> request) {
        String reviewId = request.get("reviewId");
        String reply = request.get("reply");
        return reviewService.replyReview(reviewId,reply);
    }

    @GetMapping("/count/{product-id}")
    public int getReviewCount(@PathVariable("product-id") String productId) {
        return reviewService.getReviewCount(productId);
    }
}
