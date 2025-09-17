package com.example.customer_service.Address.modal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDTO {
    private String id;
    private String name;
    private String phone;
    private String city;
    private String ward;
    private String streetAddress;
    private String customerId;
}
