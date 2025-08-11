package com.example.product_service.product;

import com.example.product_service.category.CategoryRepository;
import com.example.product_service.entity.Category;
import com.example.product_service.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryRepository categoryRepository;
    public Product toProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow();
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        return product;
    }
}
