package com.example.review.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private String avatarUrl;
    private String keyAvatar;
}
