package com.example.review.Customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8060/api/v1/customers")
public interface CustomerClient {
    @GetMapping("/{customer-id}")
    CustomerDTO getCustomerId(@PathVariable("customer-id") String customerId) ;
}
