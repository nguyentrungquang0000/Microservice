package com.example.customer_service.S3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileResponse {
    private String key;
    private String fileUrl;
    private String type;
}
