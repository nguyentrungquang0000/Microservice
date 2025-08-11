package com.example.Order_service.order.modal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class AddOrderRequest {
    private String addressId;
    private String method;
    private List<String> cartItemIds;
    private String description;
}
