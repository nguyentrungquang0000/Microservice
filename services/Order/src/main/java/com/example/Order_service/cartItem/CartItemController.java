package com.example.Order_service.cartItem;

import com.example.Order_service.cartItem.modal.request.AddCartItemRequest;
import com.example.Order_service.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService service;
    @PostMapping()
    public ResponseEntity<?> addCartItem(@RequestBody AddCartItemRequest request,
                                              @RequestHeader("X-User-Id") String userId) {
        service.addCartItem(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, "Successfully added cart item", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCartItem(@PathVariable("id") String cartItemId,
                                                 @RequestParam("quantity") int quantity){
        return service.updateCartItem(cartItemId, quantity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable("id") String cartItemId){
        return service.deleteCatItem(cartItemId);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getCartItems(@RequestHeader("X-User-Id") String userId){
        return service.getCartItems(userId);
    }
}
