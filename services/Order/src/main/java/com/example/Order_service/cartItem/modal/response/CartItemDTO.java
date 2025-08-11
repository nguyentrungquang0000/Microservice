package com.example.Order_service.cartItem.modal.response;

import com.example.Order_service.ProductOption.ProductOptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class CartItemDTO {
    private String id;
    private int quantity;
    private ProductOptionDTO productOption;
}
