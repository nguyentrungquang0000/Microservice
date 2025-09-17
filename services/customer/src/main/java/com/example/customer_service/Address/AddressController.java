package com.example.customer_service.Address;

import com.example.customer_service.Address.modal.request.AddressRequest;
import com.example.customer_service.Address.modal.response.AddressDTO;
import com.example.customer_service.Address.modal.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;

    @PostMapping()
    public ResponseEntity<Object> addAddress(@RequestHeader("X-User-Id") String userId,
                                             @RequestBody AddressRequest request) {
        return service.addAddress(request, userId);
    }

    @DeleteMapping("/{address-id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable("address-id") String addressId) {
        return service.deleteAddress(addressId);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<?>> getAddressAll(@RequestHeader("X-User-Id") String userId) {
        return service.getAddressAll(userId);
    }

    @GetMapping("/{address-id}")
    public AddressDTO getAddress(@PathVariable("address-id") String addressId) {
        return service.getAddress(addressId);
    }
}
