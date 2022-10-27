package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.CustomersAddress;
import com.api.mobigenz_be.repositories.CustomersAddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersAddressImp implements CustomersAddressService{

    @Autowired
    private CustomersAddressRepository customersAddressRepository;

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
    public Optional<CustomersAddress> findByCustomerId(Integer cid) {
        return this.customersAddressRepository.findById(cid);
    }

    @Override
    public CustomersAddress create(CustomersAddress customersAddress) {
        return this.customersAddressRepository.saveAndFlush(customersAddress);
    }

    @Override
    public CustomersAddress update(CustomersAddress customersAddress) {
        return this.customersAddressRepository.save(customersAddress);
    }

    @Override
    public void delete(Integer id) {
        this.customersAddressRepository.deleteById(id);
    }

//    @Override
//    public CustomersAddress delete2(CustomersAddress customersAddress) {
//        customersAddress.set
//        return this.customersAddressRepository.save(customersAddress);
//    }

}
