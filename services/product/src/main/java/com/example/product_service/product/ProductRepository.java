package com.example.product_service.product;

import com.example.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findProductByNameContainingIgnoreCase(String name, Pageable pageable);

    Product findProductById(String id);
}
