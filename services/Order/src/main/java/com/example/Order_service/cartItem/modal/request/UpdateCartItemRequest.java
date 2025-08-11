package com.example.Order_service.cartItem.modal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UpdateCartItemRequest {
    private String cartItemId;
    private int quantity;
}
