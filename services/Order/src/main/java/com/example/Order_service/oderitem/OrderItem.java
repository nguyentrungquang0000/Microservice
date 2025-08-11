package com.example.Order_service.oderitem;

import com.example.Order_service.order.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int quantity;
    private BigDecimal price;
    private String productOptionId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
