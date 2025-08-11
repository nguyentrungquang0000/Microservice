package com.example.Order_service.kafka;

import com.example.Order_service.Address.AddressDTO;
import com.example.Order_service.ProductOption.ProductOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderConfirmation {
    private String orderId;
    private BigDecimal amount;
    private String paymentMethod;
    private AddressDTO address;
    private List<ProductOptionResponse> productOptions;
}
