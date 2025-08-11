package com.example.customer_service.Customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequest request) {
        return service.createCustomer(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("/myinfo")
    public ResponseEntity<Object> getCustomer(@RequestHeader("X-User-Id") String userId) {
        return service.myInfo(userId);
    }


}
