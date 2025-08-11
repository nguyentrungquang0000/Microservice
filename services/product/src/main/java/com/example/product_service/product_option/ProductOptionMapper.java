package com.example.product_service.product_option;

import com.example.product_service.entity.ProductOption;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionMapper {
    public ProductOption toProductOption(ProductOptionRequest request) {
        ProductOption productOption = new ProductOption();
        productOption.setName(request.getName());
        productOption.setPrice(request.getPrice());
        productOption.setStock(request.getStock());
        productOption.setDiscount(request.getDiscount());
        return productOption;
    }
}
