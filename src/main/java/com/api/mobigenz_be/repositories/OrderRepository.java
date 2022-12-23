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

    @Query("select o from Order o where o.customer.id = :customer_id and " +
            "(:order_status = 999 or o.orderStatus = :order_status) and ( " +
            "lower(o.address) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientName) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientPhone) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientEmail) like  lower(concat('%', :term,'%')) )" +
            " order by o.ctime desc ")
    List<Order> searchOrderByCustomerId(
            @Param("term") String term,
            @Param("order_status") Integer order_status,
            @Param("customer_id") Integer customer_id
    );

    @Query("select o from Order o where " +
            "(:order_status = 999 or o.orderStatus = :order_status) and ( " +
            "lower(o.address) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientName) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientPhone) like  lower(concat('%', :term,'%')) or " +
            "lower(o.recipientEmail) like  lower(concat('%', :term,'%')) )" +
            " order by o.ctime desc ")
    List<Order> searchOrders(
            @Param("term") String term,
            @Param("order_status") Integer order_status
    );

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

    String STATISTIC_INCOME_DATE = "select  COALESCE(o1.DT_STORE, 0) as dt_store,\n" +
            "        COALESCE(o2.DT_ONLINE, 0) as dt_online,\n" +
            "        COALESCE(o1.ngay, o2.ngay) as ngay      \n" +
            "    from\n" +
            "        (select\n" +
            "            sum(orders.goods_value) as DT_STORE,\n" +
            "            EXTRACT(DAY from orders.ctime) as ngay             \n" +
            "        from orders                  \n" +
            "        where   orders.purchasetype = 0 \n" +
            "                and ( orders.order_status = 4 or orders.order_status = -2) and\n" +
            "                ((EXTRACT(MONTH from orders.ctime) = :s_month) or (EXTRACT(MONTH from orders.ctime) = :e_month)) and\n" +
            "                ((EXTRACT(YEAR from orders.ctime) = :s_year) or (EXTRACT(YEAR from orders.ctime) = :e_year))               \n" +
            "        group by\n" +
            "            EXTRACT(DAY from orders.ctime)              \n" +
            "        having\n" +
            "            EXTRACT(DAY from orders.ctime) BETWEEN :s_day and :e_day\n" +
            "        ) as o1\n" +
            "    full join\n" +
            "        (select\n" +
            "            sum(orders.goods_value) as DT_ONLINE,\n" +
            "            EXTRACT(DAY from orders.ctime) as ngay             \n" +
            "        from orders                  \n" +
            "        where   orders.purchasetype = 1 \n" +
            "                and ( orders.order_status = 4 or orders.order_status = -2) and\n" +
            "                ((EXTRACT(MONTH from orders.ctime) = :s_month) or (EXTRACT(MONTH from orders.ctime) = :e_month)) and\n" +
            "                ((EXTRACT(YEAR from orders.ctime) = :s_year) or (EXTRACT(YEAR from orders.ctime) = :e_year))               \n" +
            "        group by\n" +
            "            EXTRACT(DAY from orders.ctime)              \n" +
            "        having\n" +
            "            EXTRACT(DAY from orders.ctime) BETWEEN :s_day and :e_day\n" +
            "        ) as o2      \n" +
            "   on o1.ngay = o2.ngay\n";
    @Query(nativeQuery = true, value = STATISTIC_INCOME_DATE)
    List<StatisticIncome> getStatisticIncomeByDate(@Param("s_day") int s_day,
                                                   @Param("e_day")int e_day,
                                                   @Param("s_month")int s_month,
                                                   @Param("e_month")int e_month,
                                                   @Param("s_year")int s_year,
                                                   @Param("e_year")int e_year);


    @Query(nativeQuery = true,
            value = "select count(order_status) from orders  where order_status = 0 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 1 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 2 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 3 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = 4 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = -2 and ctime between ?1 and ?2\n" +
            "union all \n" +
            "select count(order_status) from orders  where order_status = -1 and ctime between ?1 and ?2\n"

    )

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
