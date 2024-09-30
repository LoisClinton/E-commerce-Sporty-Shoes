package com.SportyShoes.ECommerceApplication.repository;

import java.util.Optional;

import com.SportyShoes.ECommerceApplication.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByBrand(String brand);
    Product findByName(String name);
}