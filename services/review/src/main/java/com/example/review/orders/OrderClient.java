package com.example.review.orders;

import com.example.review.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "orders-service", url = "http://localhost:8040/api/v1/order")
public interface OrderClient {
    @GetMapping("/{order-id}")
    ApiResponse<OrderDTO> getOrderById(@PathVariable("order-id") String orderId);

    @PostMapping("/status")
    ApiResponse<String> putStatusOrder(@RequestBody Map<String, String> request);
}
