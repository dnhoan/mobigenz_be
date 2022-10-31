package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.PageDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.mobigenz_be.services.CustomerService;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<ResponseDTO> getPageCustomers(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<CustomerDTO> items = this.customerService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", items))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @PostMapping("customers")
    public ResponseEntity<ResponseDTO> insertCustomer(@RequestBody Customer customer) {
        System.out.println("create customer");
        try {
            CustomerDTO customerDTO = this.customerService.create(customer);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customers", customerDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<ResponseDTO> getCustomersById(@PathVariable("id") Integer id) {
        try {
            CustomerDTO customerDTO = this.customerService.getById(id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customer", customerDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(NO_CONTENT)
                            .data(Map.of("message", "Customer not exist"))
                            .statusCode(NO_CONTENT.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("customers")
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody Customer customer) {
        try {
            CustomerDTO customerDTO = this.customerService.update(customer);
            List<CustomerDTO> customerDTOList = this.customerService.searchById();
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customer", customerDTO))
                            .data(Map.of("customer", customerDTOList))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable("id")
                                                      Customer customer) {
        if (customer != null) {
            this.customerService.delete(customer);
            List<CustomerDTO> customerDTOList = this.customerService.searchById();
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .message("Delete success")
                            .status(OK)
                            .data(Map.of("customer", customerDTOList))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()

            );
        }
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Customer id not exist")
                        .status(NO_CONTENT)
                        .statusCode(NO_CONTENT.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("searchCustomerByName")
    public ResponseEntity<ResponseDTO> searchCustomerByName( @RequestParam("customerName") String customerName) {
        List<CustomerDTO> customerDTOList = this.customerService.searchByName(customerName);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customer", customerDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("searchCustomerByEmail")
    public ResponseEntity<ResponseDTO> searchCustomerByEmail( @RequestParam("email") String email) {
        List<CustomerDTO> customerDTOList = this.customerService.searchByEmail(email);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customer", customerDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("searchCustomerByPhoneNumber")
    public ResponseEntity<ResponseDTO> searchCustomerByPhoneNumber( @RequestParam("phoneNumber") String phoneNumber) {
        List<CustomerDTO> customerDTOList = this.customerService.searchByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customer", customerDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("searchCustomerByAddress")
    public ResponseEntity<ResponseDTO> searchCustomerByAddress( @RequestParam("address") String address) {
        List<CustomerDTO> customerDTOList = this.customerService.searchByAddress(address);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customer", customerDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}




