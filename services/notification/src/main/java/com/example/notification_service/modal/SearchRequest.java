package com.example.notification_service.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {
    @Builder.Default
    public String keyword="";
    @Builder.Default
    public int page = 0;
    @Builder.Default
    public int limit = 8;
    @Builder.Default
    public String direction = "des";
    @Builder.Default
    public String sortBy = "id";
    @Builder.Default
    public String category = "";
}
