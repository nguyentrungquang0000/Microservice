package com.example.customer_service.Customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setRole(request.getRole());
        return customer;
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .name(customer.getName())
                .email(customer.getEmail())
                .avatarUrl(customer.getAvatarUrl())
                .keyAvatar(customer.getKeyAvatar())
                .build();
    }
}
