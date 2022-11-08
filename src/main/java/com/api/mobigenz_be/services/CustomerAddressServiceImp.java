package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.CustomersAddressDto;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.CustomersAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerAddressServiceImp implements CustomersAddressService{

    @Autowired
    private CustomersAddressRepository customersAddressRepository;
    private Pageable pageable;

//    @Autowired
//    private ModelMapper modelMapper;

    @Override
    public List<CustomersAddress> getAll() {
        return this.customersAddressRepository.findAll();
    }

    @Override
    public Optional<CustomersAddress> findById(Integer id) {
        return this.customersAddressRepository.findById(id);
    }

    @Override
    public List<CustomersAddress> findByCustomerId(Integer cid) {
        return this.customersAddressRepository.findByCustomerId(cid);
    }

    @Override
    public List<CustomersAddress> findByCustomerName(String customerName) {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Customer> page = this.customersAddressRepository.findByCustomerName(customerName, pageable);
//        List<C> customerDTOList = page.stream().map(u -> this.modelMapper.map(u, CustomerDTO.class)).collect(Collectors.toList());
//        return customerDTOList;

        return  this.customersAddressRepository.findByCustomerName(customerName);
    }



    @Transactional
    public CustomersAddress create(CustomersAddress customersAddress) {
        customersAddress.setStatus(1);
        return this.customersAddressRepository.save(customersAddress);
    }

    @Override
    public CustomersAddress update(CustomersAddress customersAddress) {
        return this.customersAddressRepository.save(customersAddress);
    }

    @Override
    public CustomersAddress delete(CustomersAddress customersAddress) {
        customersAddress.setStatus(0);

        return this.customersAddressRepository.save(customersAddress);
    }

//    private CustomersAddressDto customersAddressMapToCustomerAddressDto(CustomersAddress customersAddress){
//        List<CustomerDTO> customerDtos = customersAddress.getCustomerId()
//                .stream()
//                .map(this::customerMapToCustomerDto)
//                .collect(Collectors.toList());
//    return CustomersAddressDto
//                .builder()
//                .id(customersAddress.getId())
//                .ward(customersAddress.getWard())
//                .city(customersAddress.getCity())
//                .district(customersAddress.getDistrict())
//                .detailAddress(customersAddress.getDetaiAddress())
//                .status(customersAddress.getStatus())
//                .build();
//
//
//    }


//    private CustomersAddress customersAddressDtoMapToCustomerAddress(CustomersAddressDto customersAddressDto){
//        CustomersAddress customersAddress = CustomersAddress
//                .builder()
//                .ward(customersAddressDto.getWard())
//                .build();
//
//
//
//        return customersAddress;
//    }

//    private CustomerDTO customerMapToCustomerDto(Customer customer) {
//        return CustomerDTO
//                .builder()
//                .id(customer.getId())
//                .customerName(customer.getCustomerName())
//                .status(customer.getStatus())
//                .build();
//    }

}
