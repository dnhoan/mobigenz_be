package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.CustomersAddressDto;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.*;
import com.api.mobigenz_be.repositories.CustomerRepository;
import com.api.mobigenz_be.repositories.CustomersAddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerAddressServiceImp implements CustomersAddressService{

    @Autowired
    private CustomersAddressRepository customersAddressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PageDTO<CustomersAddressDto> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<CustomersAddress> page = this.customersAddressRepository.getAllId(pageable);
        List<CustomersAddressDto> customerDTOList = page.stream().map(u -> this.modelMapper.map(u, CustomersAddressDto.class)).collect(Collectors.toList());
        return new PageDTO<CustomersAddressDto>(
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                customerDTOList,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
    }

    private CustomersAddress customersAddressDtoMapToCustomerAddress(CustomersAddressDto customersAddressDto){
       Customer customer = modelMapper.map(customersAddressDto.getCustomerId(), Customer.class);

        CustomersAddress customersAddress = CustomersAddress
                .builder()
                .ward(customersAddressDto.getWard())
                .district(customersAddressDto.getDistrict())
                .city(customersAddressDto.getCity())
                .detailAddress(customersAddressDto.getDetailAddress())
                .customerId(customer)
                .status(1)
                .build();
        System.out.println(customersAddress.getStatus());

        return customersAddress;
    }

    private CustomerDTO customerMapToCustomerDto(Customer customer){

        if(customer.getId() == null){
            return CustomerDTO.builder().build();
        }else{

        return CustomerDTO.builder()
                .id(customer.getId())
                .customerName(customer.getCustomerName())
                .birthday(customer.getBirthday())
                .citizenIdentifyCart(customer.getCitizenIdentifyCart())
                .image(customer.getImage())
                .customerType(customer.getCustomerType())
                .status(customer.getStatus())
                .phoneNumber(customer.getPhoneNumber())
                .ctime(customer.getCtime())
                .mtime(customer.getMtime())
                .gender(customer.getGender())
                .build();
    }}

    private CustomersAddressDto customersAddressMapToCustomerAddressDto(CustomersAddress customersAddress){
        Customer customer = this.customerRepository.getCustomerByCustomerAddressId(customersAddress.getId());

        return CustomersAddressDto.builder()
                .id(customersAddress.getId())
                .ward(customersAddress.getWard())
                .district(customersAddress.getDistrict())
                .city(customersAddress.getCity())
                .detailAddress(customersAddress.getDetailAddress())
                .customerId(this.customerMapToCustomerDto(customer))
                .build();
    }

    @Transactional
    public CustomersAddressDto createCa(CustomersAddressDto customersAddressDto){
        CustomersAddress customersAddress = this.customersAddressRepository.save(this.customersAddressDtoMapToCustomerAddress(customersAddressDto));
        return this.customersAddressMapToCustomerAddressDto(customersAddress);
    }

    @Override
    public List<CustomersAddress> getById(){

        return this.customersAddressRepository.getAllById();


    }

    @Override
    public List<CustomersAddressDto> searchById(){

        List<CustomersAddress> customersAddresses = this.customersAddressRepository.getAllById();

        return customersAddresses.stream().map(this::customersAddressMapToCustomerAddressDto).collect(Collectors.toList());


    }

    @Override
    public List<CustomersAddress> findByCustomerId(Integer cid) {
        return this.customersAddressRepository.findByCustomerId(cid);
    }

    @Override
    public List<CustomersAddress> findByCustomerName(String customerName) {

        return  this.customersAddressRepository.findByCustomerName(customerName);
    }


    @Override
    public CustomersAddressDto update(CustomersAddressDto customersAddressDto) {
        CustomersAddress customersAddress = this.customersAddressRepository.save(this.customersAddressDtoMapToCustomerAddress(customersAddressDto));
        return this.customersAddressMapToCustomerAddressDto(customersAddress);    }

    @Override
    public CustomersAddress delete(CustomersAddress customersAddress) {
        customersAddress.setStatus(0);

        return this.customersAddressRepository.save(customersAddress);
    }



}
