package com.SportyShoes.ECommerceApplication.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String brand;
    private String type;
    private double price;
    private int quantity;
    private String imageUrl;
}
