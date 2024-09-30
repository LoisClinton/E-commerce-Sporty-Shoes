package com.SportyShoes.ECommerceApplication.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String password;
    private String userType; // Either "admin" or "customer"
}