package com.example.Order_service.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    CartItem findByCustomerIdAndProductOptionId(String userId, String productOptionId);

    List<CartItem> findByCustomerId(String userId);
}
