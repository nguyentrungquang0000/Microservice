package com.example.review.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private String address;
    private String description;
    private OrderStatus status;
    private BigDecimal amount;
}