package com.api.mobigenz_be.controllers.user;


import com.api.mobigenz_be.DTOs.AccountDTO;
import com.api.mobigenz_be.DTOs.CustomerDTO;
import com.api.mobigenz_be.DTOs.ResponseDTO;
import com.api.mobigenz_be.constants.Constant;
import com.api.mobigenz_be.entities.Account;
import com.api.mobigenz_be.entities.Customer;
import com.api.mobigenz_be.entities.ResponseObject;
import com.api.mobigenz_be.repositories.CustomerRepository;
import com.api.mobigenz_be.services.AccountService;
import com.api.mobigenz_be.services.CustomerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping(value = Constant.Api.Path.USER)
@CrossOrigin("*")
public class UserController {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private AccountService accountService;


    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("customer/email")
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


    @GetMapping("customers/getAllCus")
    public ResponseEntity<ResponseDTO> getCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("customers", customers))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }


    @GetMapping("customers/email")
    public ResponseEntity<ResponseDTO> getCusByCusId(@RequestParam(value = "email") String email) {
        try {
            Account account = this.accountService.findByEmail(email);
            if (account != null) {
                Integer idCus = account.getCustomer().getId();
                System.out.println(idCus);
                Customer customer = this.customerService.getCustomerByCustomerID(idCus);
                return ResponseEntity.ok(
                        ResponseDTO.builder()
                                .status(OK)
                                .data(Map.of("customers", customer))
                                .statusCode(OK.value())
                                .timeStamp(LocalDateTime.now())
                                .build()
                );
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("customers")
    public ResponseEntity<ResponseObject> updateCustomer(@RequestBody Customer customer) {
        try {
            Optional<Customer> cus = this.customerRepository.findById(customer.getId());
            if (cus.isPresent()) {
                Account account = cus.get().getAccount();
                if(account != null){
                    account.setCtime(LocalDateTime.now());
                    account.setPhoneNumber(customer.getPhoneNumber());
                    AccountDTO accountDTO = this.accountService.add(account);
                    customer.setAccount(account);
                }
                this.customerService.update(customer);
                List<CustomerDTO> customerDTOList = this.customerService.getAllCus();
                return ResponseEntity.status(OK).body(
                        new ResponseObject("true", "Cập nhật khách hàng thành công!", customerDTOList)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(BAD_REQUEST).body(
                new ResponseObject("false", "Cập nhật thất bại!", "")
        );
    }
}
