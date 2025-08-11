package com.example.search.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    private String id;
    private List<String> imageUrl;
    private String name;
    private String description;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private float averageRating;
    private int reviewCount;
    private int soldQuantity;
    List<OptionDetail> options;
}
