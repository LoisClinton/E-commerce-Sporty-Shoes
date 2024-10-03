package com.SportyShoes.ECommerceApplication.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SportyShoes.ECommerceApplication.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT p.name, COUNT(o), o.status FROM Order o JOIN o.products p WHERE o.user.uid = :userId GROUP BY p.name, o.status")
    List<Object[]> sortAndGroupOrderProductsByUserUid(@Param("userId") int userId);

    List<Order> findAllByOid(int oid);

    @Query("SELECT o FROM Order o WHERE o.orderDate >= :startDate AND o.orderDate <= :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
