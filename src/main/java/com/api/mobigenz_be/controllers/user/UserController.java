package com.api.mobigenz_be.controllers.user;


import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(value = Constant.Api.Path.USER)
@CrossOrigin("*")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("customers/email")
    public ResponseEntity<ResponseDTO> getByEmail(@RequestParam(value = "email") String email) {
        Customer customer = this.customerService.findByEmail(email);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", customer))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

}
