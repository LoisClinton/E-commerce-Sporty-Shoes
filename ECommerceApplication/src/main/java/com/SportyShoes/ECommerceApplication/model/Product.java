package com.SportyShoes.ECommerceApplication.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "products")
public class Product {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    private String name;
    @Column(unique = true)
    private String brand;
    private String type;
    private double price;
    private int quantity;
    private String imageUrl;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @PreRemove
    private void removeOrdersFromProduct() {
        for (Order order : orders) {
            order.getProducts().remove(this);
        }
    }
}
