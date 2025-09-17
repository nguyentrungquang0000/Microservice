package com.example.product_service.category;

import com.example.product_service.entity.Category;
import com.example.search.ApiResponse;
import com.example.search.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public ResponseEntity<ApiResponse<?>> addCategory(CategoryRequest request) {
        Category category = categoryRepository.findByName(request.getName());
        if(category != null) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Category Name is exsit", null));
        }
        Category newCategory = new Category();
        newCategory.setName(request.getName());
        newCategory.setDescription(request.getDescription());
        categoryRepository.save(newCategory);
        return ResponseEntity.status(200).body(new ApiResponse<>(200, "Category Added", null));
    }

    public ResponseEntity<ApiResponse<?>> deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Category Not Found", null));
        }
        categoryRepository.deleteById(categoryId);
        return ResponseEntity.status(200).body(new ApiResponse<>(200, "Category Deleted", null));
    }

    public ResponseEntity<ApiResponse<?>> updateCategory(CategoryDTO request) {
        Category category = categoryRepository.findById(request.getId()).orElse(null);
        if(category == null) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Category Not Found", null));
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return ResponseEntity.status(200).body(new ApiResponse<>(200, "Category Updated", null));
    }

    public ResponseEntity<ApiResponse<?>> getCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Category Not Found", null));
        }
        return ResponseEntity.status(200).body(new ApiResponse<>(200, "Category Found",
                CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build()
        ));
    }

    public ResponseEntity<ApiResponse<?>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList= new ArrayList<>();
        for(Category category : categories) {
            CategoryDTO categoryDTO = CategoryDTO.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
            categoryDTOList.add(categoryDTO);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories Found", categoryDTOList));
    }
}
