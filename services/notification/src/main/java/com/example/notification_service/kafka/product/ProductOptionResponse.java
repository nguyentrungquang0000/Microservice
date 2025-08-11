package com.example.notification_service.kafka.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionResponse {
    private String productId;
    private String productName;
    private String productOptionName;
    private BigDecimal price;
    private int quantity;
}
