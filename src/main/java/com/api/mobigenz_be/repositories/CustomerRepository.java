package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer > {

    @Query("select c from Customer c order by c.id")
    Page<Customer> getAllById(Pageable pageable);

    @Query("Select c from Customer  c\n" +
            "left Join CustomersAddress as ca on ca.customerId = c.id \n" +
            "where ca.detailAddress=:search \n" +
            "or c.customerName=:search \n" +
            "or c.phoneNumber=:search \n" +
            "or c.email=:search")
    Page<Customer> findByAll(@Param("search") String search, Pageable pageable);


    @Query("SELECT cus from Customer cus where cus.account.id = :accountId")
    Customer findByAccountId(Integer accountId);

    @Query("SELECT cus from Customer cus where cus.customerName = :customerName")
    List<Customer> findByCustomerName(@Param("customerName") String customerName);

    Customer findByEmail(String email);
    //@Query(name="Customer.findByUsername")
//    @Query("select c from Customer c where c.customerName=:customer_name")
//    Page<Customer> findByCustomerName(@Param("customer_name") String customerName, Pageable pageable);
//
//    Page<Customer> findByCustomerName(String customerName, Pageable pageable);
//
//    @Query("select c from Customer c where c.email=:email")
//    Page<Customer> findByEmail(@Param("email") String email, Pageable pageable);
//
//    @Query("select c from Customer c where c.phoneNumber=:phoneNumber")
//    Page<Customer> findByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);
//
//    @Query("Select c from Customer c \n" +
//            "Join CustomersAddress as ca on ca.customerId = c.id where ca.note=:address")
//    Page<Customer> findByAddress(@Param("address") String address, Pageable pageable);



   @Query("select c from Customer c join CustomersAddress ca on c = ca.customerId where ca.id =:customer_id ")
    Customer getCustomerByCustomerAddressId(@Param("customer_id") Integer customer_id);

}
