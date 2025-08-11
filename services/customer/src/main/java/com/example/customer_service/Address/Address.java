package com.example.customer_service.Address;

import com.example.customer_service.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String phone;
    private String city;
    private String ward;
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
