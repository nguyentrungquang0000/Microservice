package com.example.product_service.product_option;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/option")
@RequiredArgsConstructor
public class ProductOptionController {
    private final ProductOptionService service;
    @GetMapping("/{option-id}")
    public ProductOptionDTO getProductOption(@PathVariable("option-id") String optionId) {
        return service.getProductOption(optionId);
    }
}
