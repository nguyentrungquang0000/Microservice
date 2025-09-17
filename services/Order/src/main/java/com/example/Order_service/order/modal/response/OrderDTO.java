package com.example.Order_service.order.modal.response;

import com.example.Order_service.order.modal.enumm.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private String address;
    private String description;
    private OrderStatus status;
    private BigDecimal amount;
    private List<OrderItemDTO> orderItemDTOS;
}

