package com.example.product_service.product;

import com.example.search.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestPart("files") List<MultipartFile> files,
                                                @RequestPart("request") ProductRequest request) {
        return service.createProduct(request, files);
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product-id") String productId) {
        return service.deleteProduct(productId);
    }

    @GetMapping()
    public ResponseEntity<?> getProducts(@ModelAttribute SearchRequest request) {
        return service.getProducts(request);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<?> getProductById(@PathVariable("product-id") String productId) {
        return service.getProductById(productId);
    }
}
