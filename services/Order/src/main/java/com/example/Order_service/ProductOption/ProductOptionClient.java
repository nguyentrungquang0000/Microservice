package com.example.Order_service.ProductOption;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8050/api/v1/option")
public interface ProductOptionClient {
    @GetMapping("/{option-id}")
    ProductOptionDTO getProductOption(@PathVariable("option-id") String optionId);
}
