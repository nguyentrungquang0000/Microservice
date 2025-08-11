package com.example.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
