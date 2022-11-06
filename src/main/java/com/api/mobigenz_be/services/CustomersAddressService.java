package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomersAddressDto;
import com.api.mobigenz_be.entities.CustomersAddress;

import java.util.List;
import java.util.Optional;

public interface CustomersAddressService {


    List<CustomersAddress> getAll();

    Optional<CustomersAddress> findById(Integer id);

    Optional<CustomersAddress> findByCustomerId(Integer cid);

    List<CustomersAddress> findByCustomerName(String customerName);

    CustomersAddress create(CustomersAddress customersAddress);

    CustomersAddress update(CustomersAddress customersAddress);

    CustomersAddress delete(CustomersAddress customersAddress);

    //CustomersAddress delete2(CustomersAddress customersAddress);

}
