package com.example.notification_service.kafka.address;

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
    private String address;
    private String customerId;
}
