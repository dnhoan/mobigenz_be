package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getOrdersByCustomerId(Integer customer_id);

}
