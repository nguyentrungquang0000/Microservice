package com.example.review.review;

import lombok.Data;

import java.util.List;

@Data
public class ReviewRequestWrapper {
    private List<ReviewRequest> request;
}
