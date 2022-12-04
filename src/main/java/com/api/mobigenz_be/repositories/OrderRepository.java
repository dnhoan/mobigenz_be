package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.StatisticIncome;
import com.api.mobigenz_be.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> getOrdersByCustomerIdOrderByCtimeDesc(Integer customer_id);

    @Query("select o from Order o where (:orderStatus is null or o.orderStatus = :orderStatus) order by o.ctime desc ")
    List<Order> getOrderByOrderStatus(Integer orderStatus);


    @Query(nativeQuery = true, value = "select COALESCE(o1.DT_STORE, 0) as dt_store, COALESCE(o2.DT_ONLINE, 0) as dt_online, COALESCE(o1.thang, o2.thang) as thang from (\n" +
            "       select sum(orders.total_money) as DT_STORE, EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
            "        from orders\n" +
            "        where orders.purchasetype = 0\n" +
            "        group by EXTRACT(YEAR  from orders.ctime), EXTRACT(MONTH from orders.ctime)\n" +
            "        having EXTRACT(YEAR  from orders.ctime) = ?1\n" +
            ") as o1 full join (\n" +
            "        select sum(orders.total_money) as DT_ONLINE,EXTRACT(YEAR  from orders.ctime) nam, EXTRACT(MONTH from orders.ctime) as thang \n" +
            "        from orders\n" +
            "        where orders.purchasetype = 1\n" +
            "        group by EXTRACT(YEAR  from orders.ctime), EXTRACT(MONTH from orders.ctime), EXTRACT(MONTH from orders.ctime)\n" +
            "        having EXTRACT(YEAR  from orders.ctime) = ?1\n" +
            ") as o2 on o1.thang = o2.thang")
    List<StatisticIncome> getStatisticIncomeByYear(Integer year);


    @Query(nativeQuery = true,
            value = "select count(order_status) from orders  where order_status = -2 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = -1 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 1 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 2 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 3 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 4 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 5 and ctime between ?1 and ?2")
    List<Integer> statisticOrderStatus(Date s_date, Date e_date);

    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderStatus = :newOrderStatus,  o.cancelNote = :note where o.id = :order_id")
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
