package com.example.search.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDetail {
    private String id;
    private String name;
    private int discount;
    private BigDecimal price;
    private int stock;
}
