package com.example.Order_service.order.modal.response;

import com.example.Order_service.ProductOption.ProductOptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class OrderItemDTO {
    private String id;
    private int quantity;
    private BigDecimal price;
    private ProductOptionDTO productOption;
}
