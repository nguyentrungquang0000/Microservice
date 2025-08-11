package com.example.Order_service.order;

import com.example.Order_service.order.modal.request.AddOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping()
    public ResponseEntity<Object> addOrder(@RequestBody AddOrderRequest request,
                                           @RequestHeader("X-User-Id") String userId) {
        return service.addOrder(request, userId);
    }
}
