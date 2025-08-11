package com.example.Order_service.cartItem;

import com.example.Order_service.ApiResponse;
import com.example.Order_service.ProductOption.ProductOptionClient;
import com.example.Order_service.cartItem.modal.request.AddCartItemRequest;
import com.example.Order_service.cartItem.modal.response.CartItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductOptionClient productOptionClient;
    public ResponseEntity<Object> addCartItem(String userId, AddCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductOptionId(userId, request.getProductOptionId());
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        }else{
            cartItem = CartItem.builder()
                    .productOptionId(request.getProductOptionId())
                    .quantity(request.getQuantity())
                    .customerId(userId)
                    .build();
        }
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok(new ApiResponse<>(200, "Successfully added cart item", null));
    }

    public ResponseEntity<Object> updateCartItem(String cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return ResponseEntity.ok("ok!");
    }

    public ResponseEntity<Object> deleteCatItem(String cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok("ok");
    }

    public ResponseEntity<ApiResponse<?>> getCartItems(String userId) {
        List<CartItem> cartItems = cartItemRepository.findByCustomerId(userId);
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(cartItem.getId());
            cartItemDTO.setQuantity(cartItem.getQuantity());
            cartItemDTO.setProductOption(productOptionClient.getProductOption(cartItem.getProductOptionId()));
            cartItemDTOList.add(cartItemDTO);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", cartItemDTOList));
    }
}
