package com.example.product_service.product_option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductOptionRequest {
    private String name;
    private BigDecimal price;
    private int stock;
    private int discount;
}
