package com.example.review.review.review_response;

import com.example.review.Customer.CustomerDTO;
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
public class ReviewResponse {

    private String id;

    private String content;
    private float rating;
    private String reply;
    private LocalDateTime createdAt;
    //file
    private List<ReviewFileResponse> files;
    //user
    private CustomerDTO customer;
}
