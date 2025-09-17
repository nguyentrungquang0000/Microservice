package com.example.Order_service.order;

import com.example.Order_service.oderitem.OrderItem;
import com.example.Order_service.order.modal.enumm.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal amount;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String method;
    private String customerId;
    private String paymentId;
    private String phone;
    private String city;
    private String ward;
    private String address;
    private String name;
    private String description;
    private LocalDateTime deliveredAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems;
}
