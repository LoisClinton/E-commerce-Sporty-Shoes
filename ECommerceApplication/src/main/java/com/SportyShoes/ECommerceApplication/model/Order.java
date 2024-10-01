package com.SportyShoes.ECommerceApplication.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The customer who placed the order

    @ManyToMany
    @JoinTable(
            name = "product_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products; // The list of products in this order

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String status; // e.g., "Pending", "Shipped", "Delivered", "Cancelled"
}
