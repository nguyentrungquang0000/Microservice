package com.example.Order_service.cartItem.modal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AddCartItemRequest {
    private String productOptionId;
    private int quantity;
}
