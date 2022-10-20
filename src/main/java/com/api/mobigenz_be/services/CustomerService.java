package com.api.mobigenz_be.services;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.entities.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api.mobigenz_be.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    public PageDTO<CustomerDTO> getAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Customer> page = this.customerRepo.findAll(pageable);
        List<CustomerDTO> customerDTOList = page.stream().map(u -> this.modelMapper.map(u, CustomerDTO.class)).collect(Collectors.toList());
        return new PageDTO<CustomerDTO>(
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

    public CustomerDTO create(Customer customer) throws Exception{
        try {
            this.customerRepo.saveAndFlush(customer);
            return this.modelMapper.map(customer, CustomerDTO.class);
        }catch (Exception exception){
            throw new Exception("Create Customer False");
        }



    }

    public CustomerDTO update(Customer customer){
        Customer ctm = this.customerRepo.save(customer);
        return this.modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO delete(Customer customer){
        customer.setStatus(0);
        this.customerRepo.save(customer);
        return  this.modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO getById(Integer id) throws Exception {
        Optional<Customer> customer = this.customerRepo.findById(id);
        if (customer.isPresent()) {
            return this.modelMapper.map(customer.get(), CustomerDTO.class);
        }
        throw new Exception("Not found category by id");
    }


}