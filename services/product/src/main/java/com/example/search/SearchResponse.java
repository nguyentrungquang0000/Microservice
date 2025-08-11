package com.example.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {
    private String id;
    private String imageUrl;
    private String name;
    private BigDecimal priceMin;
    private BigDecimal priceMax;
    private float averageRating;
    private int soldQuantity;
}
