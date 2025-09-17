package com.example.customer_service.Address.modal.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
}
