package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.Order;

import com.api.mobigenz_be.entities.ProductDetail;

import com.api.mobigenz_be.entities.Product;
import com.api.mobigenz_be.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getOrdersByCustomerId(Integer customer_id);

    @Query("select o from Order o where (:orderStatus is null or o.orderStatus = :orderStatus)")
    List<Order> getOrderByOrderStatus(Integer orderStatus);

    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderStatus = :newOrderStatus, o.cancelNote = :note where o.id = :order_id")
    void updateOrderStatus(
                    @Param("newOrderStatus") Integer newOrderStatus,
                     @Param("note") String note,
                     @Param("order_id") Integer order_id);




    @Query("select pd.productName, sum(od.amount) as amount,sum(od.priceSell) as priceSell from OrderDetail od " +
            "join Order odr on od.order.id = odr.id " +
            "join ProductDetail pds on od.productDetail.id = pds.id " +
            "join Product pd on pds.product.id = pd.id " +
            "where odr.orderStatus =5 " +
            "group by pd.productName " +
            "order by amount desc ")
    List<Object[]> statisticsByBestSellingProducts();


}
