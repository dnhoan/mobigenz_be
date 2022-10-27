package com.api.mobigenz_be.repositories;

import com.api.mobigenz_be.entities.CustomersAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomersAddressRepository extends JpaRepository<CustomersAddress, Integer> {
    @Query("SELECT ca FROM CustomersAddress ca WHERE ca.customerId = ?1")
    List<CustomersAddress> findByCustomerId(String cid);
}
