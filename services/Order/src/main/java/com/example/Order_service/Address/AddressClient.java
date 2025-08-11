package com.example.Order_service.Address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost:8060/api/v1/address")
public interface AddressClient {
    @GetMapping("/{address-id}")
    AddressDTO getAddress(@PathVariable("address-id") String addressId);
}
