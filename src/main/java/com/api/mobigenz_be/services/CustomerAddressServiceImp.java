package com.api.mobigenz_be.services;

import com.api.mobigenz_be.entities.CustomersAddress;
import com.api.mobigenz_be.repositories.CustomersAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<CustomersAddress> findByCustomerId(Integer cid) {
        return this.customersAddressRepository.findById(cid);
    }

    @Override
    public List<CustomersAddress> findByCustomerName(String customerName) {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Customer> page = this.customersAddressRepository.findByCustomerName(customerName, pageable);
//        List<C> customerDTOList = page.stream().map(u -> this.modelMapper.map(u, CustomerDTO.class)).collect(Collectors.toList());
//        return customerDTOList;

        return  this.customersAddressRepository.findByCustomerName(customerName);
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
