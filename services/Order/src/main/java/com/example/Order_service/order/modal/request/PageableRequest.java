package com.example.Order_service.order.modal.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PageableRequest {
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int limit = 15;
    @Builder.Default
    private String sortBy = "created_at";
    @Builder.Default
    private String sort = "asc";
    @Builder.Default
    private String status = "WAITING_CONFIRMATION";

}
