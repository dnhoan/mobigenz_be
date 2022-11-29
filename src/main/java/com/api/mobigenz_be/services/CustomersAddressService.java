package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomersAddressDto;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.CustomersAddress;

import java.util.List;
import java.util.Optional;

public interface CustomersAddressService {


    PageDTO<CustomersAddressDto> getAll(int offset, int limit);


    List<CustomersAddressDto> findByCustomerId(Integer cid);

    List<CustomersAddress> findByCustomerName(String customerName);

    CustomersAddressDto createCa(CustomersAddressDto customersAddressDto) ;

    CustomersAddressDto saveAddressByCustomerId(CustomersAddressDto customersAddressDto, Integer customerId);

    boolean deleteCustomerAddress(Integer addressId);

    List<CustomersAddressDto> searchById();

    List<CustomersAddress> getById();

    //CustomersAddress delete2(CustomersAddress customersAddress);

}
