package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> getOrderDetailByOrderId(Integer order_id);

}
