package com.example.product_service.product;

import com.example.product_service.product_option.ProductOptionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private List<ProductOptionRequest> options;
    private String categoryId;
}
