package com.api.mobigenz_be.controllers.admin;

import com.api.mobigenz_be.DTOs.*;
import com.api.mobigenz_be.entities.CustomersAddress;
import com.api.mobigenz_be.services.CustomersAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomersAddressController {

    @Autowired
    private CustomersAddressService customersAddressService;


    @GetMapping("customersAddress1")
    public ResponseEntity<ResponseDTO> getCustomersAddress() {
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customersAddress", this.customersAddressService.searchById()))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("customersAddress")
    public ResponseEntity<ResponseDTO> getCustomersAddress1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<CustomersAddressDto> customersAddressList = this.customersAddressService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customersAddress", customersAddressList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


//    @GetMapping("customersAddress")
//    public List<CustomersAddress> getALL(){
//        return this.customersAddressService.getAll();
//    }



    @PostMapping("customersAddress")
    public ResponseEntity<ResponseDTO> createCa(@RequestBody CustomersAddressDto customersAddressDto) {
        System.out.println("create address ");
        try {
            CustomersAddressDto customersAddress1 = this.customersAddressService.createCa(customersAddressDto);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(CREATED)
                            .data(Map.of("customersAddress", customersAddress1))
                            .statusCode(CREATED.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("customersAddress/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody CustomersAddressDto customersAddressDto) {
        try {
            CustomersAddressDto customersAddress1 = this.customersAddressService.update(customersAddressDto);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("customersAddress", customersAddress1))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("customersAddress/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") CustomersAddress customersAddress ) {

        if (customersAddress != null) {
            this.customersAddressService.delete(customersAddress);
            List<CustomersAddress> customersAddressList = this.customersAddressService.getById();
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .message("Delete success")
                            .status(OK)
                            .data(Map.of("customersAddress",customersAddressList ))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()

            );
        }
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .message("Customer Address id not exist")
                        .status(NO_CONTENT)
                        .statusCode(NO_CONTENT.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );



    }

    @GetMapping("customersAddressByCustomerName")
    public ResponseEntity<ResponseDTO> getByCustomerName(@RequestParam("customerName") String customerName) {
        List<CustomersAddress> customersAddresses = this.customersAddressService.findByCustomerName(customerName);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("customeeAddress", customersAddresses))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("customersAddressByCustomerId")
    public ResponseEntity<ResponseDTO> getByCustomerId(@RequestParam("customerId") Integer cId) {
        List<CustomersAddress> customersAddresses = this.customersAddressService.findByCustomerId(cId);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .data(Map.of("customersAddresses", customersAddresses))
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
