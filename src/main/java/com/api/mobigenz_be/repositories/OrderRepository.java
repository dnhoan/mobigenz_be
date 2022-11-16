package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getOrdersByCustomerId(Integer customer_id);

    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderStatus = :newOrderStatus, o.cancelNote = :note where o.id = :order_id")
    void updateOrderStatus(
                    @Param("newOrderStatus") Integer newOrderStatus,
                     @Param("note") String note,
                     @Param("order_id") Integer order_id);
}
