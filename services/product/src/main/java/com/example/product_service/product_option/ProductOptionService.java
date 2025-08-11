package com.example.product_service.product_option;

import com.example.product_service.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    public ProductOptionDTO getProductOption(String optionId) {
        ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow();
        return ProductOptionDTO.builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .price(productOption.getPrice())
                .discount(productOption.getDiscount())
                .stock(productOption.getStock())
                .productId(productOption.getProduct().getId())
                .productName(productOption.getProduct().getName())
                .productUrl("")
                .build();
    }
}
