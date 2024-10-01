package com.revature.revshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductResponse {
    
    private Long productId;           // Auto-increment primary key
    private String productName;       // Product name
    private String productDescription; // Product description
    private String productImageUrl;   // URL of the product image
    private String userid;            // sellerId
    private Long categoryId;          // Category ID
    private Double price;             // Product price

}
