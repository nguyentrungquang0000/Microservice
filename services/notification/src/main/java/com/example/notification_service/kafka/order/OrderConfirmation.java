package com.example.notification_service.kafka.order;

import com.example.notification_service.kafka.address.AddressDTO;
import com.example.notification_service.kafka.product.ProductOptionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class OrderConfirmation {
    private String orderId;
    private BigDecimal amount;
    private String paymentMethod;
    private AddressDTO address;
    private List<ProductOptionResponse> productOptions;
}
