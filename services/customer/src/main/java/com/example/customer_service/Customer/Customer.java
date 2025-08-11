package com.example.customer_service.Customer;

import com.example.customer_service.Address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private String role;
    private String avatarUrl;
    private String keyAvatar;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Address> addresses;
}
