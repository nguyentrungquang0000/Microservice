package com.example.customer_service.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    @Builder.Default
    private String role = "USER";
}
