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

    List<Order> getOrdersByCustomerIdOrderByCtimeDesc(Integer customer_id);

    @Query("select o from Order o where (:orderStatus is null or o.orderStatus = :orderStatus) order by o.ctime desc ")
    List<Order> getOrderByOrderStatus(Integer orderStatus);

    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderStatus = :newOrderStatus, o.cancelNote = :note where o.id = :order_id")
    void updateOrderStatus(
                    @Param("newOrderStatus") Integer newOrderStatus,
                     @Param("note") String note,
                     @Param("order_id") Integer order_id);
}
