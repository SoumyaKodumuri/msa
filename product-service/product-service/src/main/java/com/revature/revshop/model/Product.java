package com.revature.revshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; // auto increment primary key

    @Column(name = "productname", nullable = false)
    private String productName; // product name

    @Column(name = "productdescription", length = 500) // Adjust length as needed
    private String productDescription; // product description

    @Column(name = "productimageurl")
    private String productImageUrl; // URL of product image

    @Column(name = "userid", nullable = false)
    private String userid; // foreign key to sellerid

    @ManyToOne
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category; // relationship with Category

    @Column(name = "price", nullable = false)
    private Double price; // product price


}








