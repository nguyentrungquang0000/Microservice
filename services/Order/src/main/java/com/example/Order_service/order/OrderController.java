package com.example.Order_service.order;

import com.example.Order_service.ApiResponse;
import com.example.Order_service.order.modal.request.AddOrderRequest;
import com.example.Order_service.order.modal.request.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> addOrder(@RequestBody AddOrderRequest request,
                                                @RequestHeader("X-User-Id") String userId) {
        return service.addOrder(request, userId);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable("order-id") String orderId) {
        return service.getOrderById(orderId);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<?>> getAllOrders(@RequestHeader("X-User-Id") String userId,
                                                       PageableRequest pageableRequest) {
        return service.getAllOrders(userId, pageableRequest);
    }

    @PostMapping("/status")
    public ResponseEntity<ApiResponse<?>> putStatusOrder(@RequestBody Map<String, String> request){
        String orderId = request.get("orderId");
        String status = request.get("status");
        return service.putStatusOrder(orderId, status);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getOrders(@ModelAttribute PageableRequest request) {
        return service.getOrders(request);
    }

}
