package com.example.product_service.category;

import com.example.search.ApiResponse;
import com.example.search.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;
    @PostMapping
    public ResponseEntity<ApiResponse<?>> addCategory(@RequestBody CategoryRequest request) {
        return service.addCategory(request);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(@PathVariable("category-id") String categoryId) {
        return service.deleteCategory(categoryId);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<?>> updateCategory(@RequestBody CategoryDTO request) {
        return service.updateCategory(request);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<ApiResponse<?>> getCategory(@PathVariable("category-id") String categoryId) {
        return service.getCategory(categoryId);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getCategories() {
        return service.getCategories();
    }
}
