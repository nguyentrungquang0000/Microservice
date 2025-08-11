package com.example.Order_service.ProductOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductOptionDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private int stock;
    private int discount;
    private String productId;
    private String productName;
    private String productUrl;
}
