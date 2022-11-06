package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.entities.CustomersAddress;
import com.api.mobigenz_be.services.CustomersAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomersAddressController {

    @Autowired
    private CustomersAddressService customersAddressService;


    @GetMapping("customersAddress")
    public List<CustomersAddress> getCustomersAddress() {
       return this.customersAddressService.getAll();
    }


    @PostMapping("customersAddress")
    public CustomersAddress create(@RequestBody CustomersAddress customersAddress) {
        return customersAddressService.create(customersAddress);
    }

    @PutMapping("customersAddress/{id}")
    public CustomersAddress update(@PathVariable("id") Integer id ,@RequestBody CustomersAddress customersAddress) {
        return customersAddressService.update(customersAddress);
    }

    @DeleteMapping("customersAddress/{id}")
    public void delete(@PathVariable("id") CustomersAddress customersAddress ) {
        this.customersAddressService.delete(customersAddress);
    }

//    @GetMapping("customersAddress/{id}")
//    public Optional<CustomersAddress> getOne(@PathVariable("id") Integer id) {
//        return customersAddressService.findById(id);
//    }

//    @GetMapping("customersAddress/CId/{cid}")
//    public Optional<CustomersAddress> getByCustomerId(@PathVariable("cid") Integer cid) {
//        return customersAddressService.findByCustomerId(cid);
//    }

    @GetMapping("customersAddressByCustomerName")
    public List<CustomersAddress> getByCustomerName(@RequestParam("customerName") String customerName) {
        return customersAddressService.findByCustomerName(customerName);
    }

    @GetMapping("customersAddressByCustomerId")
    public Optional<CustomersAddress> getByCustomerId(@RequestParam("customerId") Integer cId) {
        return customersAddressService.findByCustomerId(cId);
    }
}
