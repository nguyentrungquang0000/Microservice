package com.example.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product_file")
public class ProductFile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fileUrl;
    private String type;
    private boolean id_key;
    private String key;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
