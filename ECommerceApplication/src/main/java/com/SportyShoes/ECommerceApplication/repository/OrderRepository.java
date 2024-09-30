package com.SportyShoes.ECommerceApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.SportyShoes.ECommerceApplication.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {}
